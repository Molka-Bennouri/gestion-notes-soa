import Auth from './auth.js';
import Api from './api.js';
import AdminView from './views/admin.js';
import TeacherView from './views/teacher.js';
import StudentView from './views/student.js';

class App {
    constructor() {
        this.appElement = document.getElementById('app');
        this.init();
    }

    async init() {
        if (!Auth.isAuthenticated()) {
            this.renderLogin();
        } else {
            this.renderDashboard();
        }
    }

    renderLogin() {
        this.appElement.innerHTML = `
            <div class="login-container">
                <div class="card login-card fade-in">
                    <div class="login-header">
                        <h1>Gestion Notes</h1>
                        <p>Connectez-vous à votre espace</p>
                    </div>
                    <form id="loginForm">
                        <div class="input-group">
                            <label>Type de compte</label>
                            <select id="userType" class="input-control">
                                <option value="admin">Administrateur</option>
                                <option value="etudiant">Étudiant</option>
                                <option value="enseignant">Enseignant</option>
                            </select>
                        </div>
                        <div class="input-group">
                            <label>Email</label>
                            <input type="email" id="email" class="input-control" required placeholder="votre@email.com">
                        </div>
                        <div class="input-group">
                            <label>Mot de passe</label>
                            <input type="password" id="password" class="input-control" required placeholder="••••••••">
                        </div>
                        <button type="submit" class="btn btn-primary" style="width: 100%">
                            Se connecter <i class="fas fa-arrow-right"></i>
                        </button>
                    </form>
                </div>
            </div>
        `;

        document.getElementById('loginForm').addEventListener('submit', this.handleLogin.bind(this));
    }

    async handleLogin(e) {
        e.preventDefault();
        const type = document.getElementById('userType').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        try {
            let user;
            if (type === 'admin') {
                const response = await Api.loginAdmin({ email, password });
                if (response.success) {
                    // Mock user object for admin since login response might be just success/fail
                    user = { email, role: 'ADMIN', ...response };
                } else {
                    throw new Error(response.message);
                }
            } else if (type === 'etudiant') {
                const response = await Api.loginEtudiant({ email, password });
                if (response.success) {
                    user = { email, role: 'ETUDIANT', ...response };
                } else {
                    throw new Error(response.message);
                }
            } else if (type === 'enseignant') {
                const response = await Api.loginEnseignant({ email, password });
                if (response.success) {
                    user = { email, role: 'ENSEIGNANT', ...response };
                } else {
                    throw new Error(response.message);
                }
            }

            Auth.login(user, type);
            this.renderDashboard();

        } catch (error) {
            alert(error.message);
        }
    }

    renderDashboard() {
        const userType = Auth.getUserType();

        if (userType === 'admin') {
            new AdminView(this.appElement);
        } else if (userType === 'etudiant') {
            new StudentView(this.appElement);
        } else if (userType === 'enseignant') {
            window.teacherView = new TeacherView(this.appElement);
        }
    }
}

new App();
