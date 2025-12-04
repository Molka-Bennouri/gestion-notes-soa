import Api from '../api.js';
import Auth from '../auth.js';

class StudentView {
    constructor(rootElement) {
        this.rootElement = rootElement;
        this.currentSection = 'dashboard';
        this.student = null;
        this.notes = [];
        this.modules = [];
        this.moyennes = {};
        this.render();
    }

    async render() {
        const user = Auth.getUser();

        this.rootElement.innerHTML = `
            <div class="dashboard-layout fade-in">
                <aside class="sidebar">
                    <div class="sidebar-brand">
                        <i class="fas fa-university"></i> Gestion Notes
                    </div>
                    <ul class="nav-links">
                        <li class="nav-item">
                            <a href="#" class="nav-link active" data-section="dashboard">
                                <i class="fas fa-chart-line"></i> Tableau de bord
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link" data-section="notes">
                                <i class="fas fa-clipboard-list"></i> Mes Notes
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link" data-section="moyennes">
                                <i class="fas fa-calculator"></i> Mes Moyennes
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link" data-section="profile">
                                <i class="fas fa-user"></i> Mon Profil
                            </a>
                        </li>
                    </ul>
                    <div class="user-profile">
                        <div class="user-avatar">E</div>
                        <div>
                            <div style="font-weight: 500">Étudiant</div>
                            <div style="font-size: 0.8rem; color: var(--text-secondary)">${user.email}</div>
                        </div>
                        <button id="logoutBtn" class="btn btn-secondary" style="margin-left: auto; padding: 0.5rem;">
                            <i class="fas fa-sign-out-alt"></i>
                        </button>
                    </div>
                </aside>
                <main class="main-content" id="mainContent">
                    <div style="text-align: center; padding: 3rem;">
                        <i class="fas fa-spinner fa-spin" style="font-size: 3rem; color: var(--primary-color);"></i>
                        <p style="margin-top: 1rem; color: var(--text-secondary);">Chargement de vos données...</p>
                    </div>
                </main>
            </div>
        `;

        // Event listeners
        document.getElementById('logoutBtn').addEventListener('click', () => Auth.logout());

        document.querySelectorAll('.nav-link').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
                link.classList.add('active');
                const section = link.getAttribute('data-section');
                this.currentSection = section;
                this.loadSection(section);
            });
        });

        // Load initial data
        await this.loadInitialData();
        this.loadSection('dashboard');
    }

    async loadInitialData() {
        try {
            const user = Auth.getUser();

            // Get student info - we need the student ID from the user object
            // Assuming the login response includes student data
            if (user.id) {
                this.student = await Api.getEtudiantById(user.id);
            } else {
                // If no ID in user, try to find by email
                const allStudents = await Api.getAllEtudiants();
                this.student = allStudents.find(s => s.email === user.email);
            }

            if (!this.student) {
                throw new Error('Impossible de récupérer les informations de l\'étudiant');
            }

            // Get published notes for this student
            this.notes = await Api.getPublishedNotesByEtudiant(this.student.id);

            // Get all modules
            this.modules = await Api.getAllModules();

            // Calculate averages per module
            await this.calculateAverages();

        } catch (error) {
            console.error('Error loading student data:', error);
            document.getElementById('mainContent').innerHTML = `
                <div class="card" style="border-color: var(--error-color);">
                    <h3 style="color: var(--error-color);">Erreur</h3>
                    <p>Impossible de charger vos données: ${error.message}</p>
                </div>
            `;
        }
    }

    async calculateAverages() {
        // Group notes by module
        const notesByModule = {};
        this.notes.forEach(note => {
            if (!notesByModule[note.moduleId]) {
                notesByModule[note.moduleId] = [];
            }
            notesByModule[note.moduleId].push(note);
        });

        // Calculate weighted average for each module
        this.moyennes = {};
        for (const moduleId in notesByModule) {
            const notes = notesByModule[moduleId];
            const totalPoints = notes.reduce((sum, n) => sum + (n.valeur * n.coefficient), 0);
            const totalCoef = notes.reduce((sum, n) => sum + n.coefficient, 0);
            this.moyennes[moduleId] = totalCoef > 0 ? (totalPoints / totalCoef).toFixed(2) : 0;
        }

        // Calculate general average
        if (this.notes.length > 0) {
            const totalPoints = this.notes.reduce((sum, n) => sum + (n.valeur * n.coefficient), 0);
            const totalCoef = this.notes.reduce((sum, n) => sum + n.coefficient, 0);
            this.moyennes.generale = totalCoef > 0 ? (totalPoints / totalCoef).toFixed(2) : 0;
        } else {
            this.moyennes.generale = 0;
        }
    }

    loadSection(section) {
        const mainContent = document.getElementById('mainContent');

        switch (section) {
            case 'dashboard':
                this.renderDashboard(mainContent);
                break;
            case 'notes':
                this.renderNotes(mainContent);
                break;
            case 'moyennes':
                this.renderMoyennes(mainContent);
                break;
            case 'profile':
                this.renderProfile(mainContent);
                break;
        }
    }

    renderDashboard(container) {
        const totalNotes = this.notes.length;
        const modulesWithNotes = new Set(this.notes.map(n => n.moduleId)).size;
        const moyenneGenerale = this.moyennes.generale || 0;
        const bestNote = this.notes.length > 0 ? Math.max(...this.notes.map(n => n.valeur)) : 0;

        container.innerHTML = `
            <div class="header">
                <h2>Bienvenue, ${this.student ? this.student.prenom : 'Étudiant'} !</h2>
            </div>

            <div class="grid-cols-2" style="grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));">
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Moyenne Générale</h3>
                        <i class="fas fa-chart-line" style="color: var(--primary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${moyenneGenerale}</div>
                    <div style="color: ${moyenneGenerale >= 10 ? 'var(--success-color)' : 'var(--error-color)'}; font-size: 0.9rem;">
                        <i class="fas fa-${moyenneGenerale >= 10 ? 'check' : 'exclamation-triangle'}"></i> ${moyenneGenerale >= 10 ? 'Admis' : 'À améliorer'}
                    </div>
                </div>
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Total Notes</h3>
                        <i class="fas fa-clipboard-list" style="color: var(--secondary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${totalNotes}</div>
                    <div style="color: var(--text-secondary); font-size: 0.9rem;">
                        <i class="fas fa-book"></i> ${modulesWithNotes} modules
                    </div>
                </div>
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Meilleure Note</h3>
                        <i class="fas fa-trophy" style="color: var(--primary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${bestNote}</div>
                    <div style="color: var(--success-color); font-size: 0.9rem;">
                        <i class="fas fa-star"></i> Sur 20
                    </div>
                </div>
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Modules Suivis</h3>
                        <i class="fas fa-book-open" style="color: var(--secondary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${modulesWithNotes}</div>
                    <div style="color: var(--text-secondary); font-size: 0.9rem;">
                        <i class="fas fa-graduation-cap"></i> Actifs
                    </div>
                </div>
            </div>

            <div class="card" style="margin-top: 2rem;">
                <h3>Notes Récentes</h3>
                ${this.renderNotesTable(this.notes.slice(-5).reverse())}
            </div>

            <div class="card" style="margin-top: 2rem;">
                <h3>Aperçu des Moyennes par Module</h3>
                <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 1rem; margin-top: 1rem;">
                    ${Object.keys(this.moyennes).filter(k => k !== 'generale').map(moduleId => {
            const module = this.modules.find(m => m.id === parseInt(moduleId));
            const moyenne = this.moyennes[moduleId];
            return `
                            <div style="padding: 1rem; background: var(--surface-dark); border-radius: 0.5rem; border-left: 3px solid ${moyenne >= 10 ? 'var(--success-color)' : 'var(--error-color)'};">
                                <div style="font-size: 0.85rem; color: var(--text-secondary); margin-bottom: 0.5rem;">${module ? module.nom : 'Module ' + moduleId}</div>
                                <div style="font-size: 1.5rem; font-weight: 600;">${moyenne}/20</div>
                            </div>
                        `;
        }).join('') || '<p style="color: var(--text-secondary);">Aucune moyenne disponible</p>'}
                </div>
            </div>
        `;
    }

    renderNotes(container) {
        container.innerHTML = `
            <div class="header">
                <h2>Mes Notes</h2>
            </div>

            <div class="card">
                <div class="input-group" style="max-width: 300px;">
                    <label>Filtrer par module</label>
                    <select id="filterModule" class="input-control">
                        <option value="">Tous les modules</option>
                        ${this.modules.map(m => `<option value="${m.id}">${m.nom}</option>`).join('')}
                    </select>
                </div>
                ${this.renderNotesTable(this.notes)}
            </div>
        `;

        document.getElementById('filterModule').addEventListener('change', (e) => {
            const moduleId = e.target.value;
            const filtered = moduleId ? this.notes.filter(n => n.moduleId === parseInt(moduleId)) : this.notes;
            const tableContainer = container.querySelector('.card');
            const filterDiv = tableContainer.querySelector('.input-group').parentElement;
            tableContainer.innerHTML = '';
            tableContainer.appendChild(filterDiv);
            tableContainer.innerHTML += this.renderNotesTable(filtered);
        });
    }

    renderNotesTable(notes) {
        if (notes.length === 0) {
            return '<p style="color: var(--text-secondary); text-align: center; padding: 2rem;">Aucune note publiée</p>';
        }

        return `
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Module</th>
                            <th>Type</th>
                            <th>Note</th>
                            <th>Coefficient</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${notes.map(note => {
            const module = this.modules.find(m => m.id === note.moduleId);
            const noteColor = note.valeur >= 10 ? 'var(--success-color)' : 'var(--error-color)';
            return `
                                <tr>
                                    <td><strong>${module ? module.nom : 'N/A'}</strong></td>
                                    <td>${note.typeEvaluation}</td>
                                    <td><strong style="color: ${noteColor}; font-size: 1.1rem;">${note.valeur}/20</strong></td>
                                    <td>${note.coefficient}</td>
                                    <td>${note.dateCreation ? new Date(note.dateCreation).toLocaleDateString('fr-FR') : 'N/A'}</td>
                                </tr>
                            `;
        }).join('')}
                    </tbody>
                </table>
            </div>
        `;
    }

    renderMoyennes(container) {
        container.innerHTML = `
            <div class="header">
                <h2>Mes Moyennes</h2>
            </div>

            <div class="card" style="background: linear-gradient(135deg, rgba(99, 102, 241, 0.1), rgba(236, 72, 153, 0.1)); border: 2px solid var(--primary-color);">
                <div style="text-align: center;">
                    <h3 style="color: var(--text-secondary); font-size: 1rem; margin-bottom: 1rem;">Moyenne Générale</h3>
                    <div style="font-size: 4rem; font-weight: 700; background: linear-gradient(to right, var(--primary-color), var(--secondary-color)); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">
                        ${this.moyennes.generale || 0}
                    </div>
                    <div style="font-size: 1.2rem; color: var(--text-secondary); margin-top: 0.5rem;">sur 20</div>
                    <div style="margin-top: 1rem; padding: 0.5rem 1rem; display: inline-block; border-radius: 2rem; background: ${this.moyennes.generale >= 10 ? 'var(--success-color)' : 'var(--error-color)'}; color: white;">
                        <i class="fas fa-${this.moyennes.generale >= 10 ? 'check-circle' : 'exclamation-circle'}"></i>
                        ${this.moyennes.generale >= 10 ? 'Admis' : 'Non admis'}
                    </div>
                </div>
            </div>

            <div style="margin-top: 2rem;">
                <h3 style="margin-bottom: 1rem;">Moyennes par Module</h3>
                <div style="display: grid; gap: 1rem;">
                    ${Object.keys(this.moyennes).filter(k => k !== 'generale').map(moduleId => {
            const module = this.modules.find(m => m.id === parseInt(moduleId));
            const moyenne = parseFloat(this.moyennes[moduleId]);
            const moduleNotes = this.notes.filter(n => n.moduleId === parseInt(moduleId));

            return `
                            <div class="card">
                                <div style="display: flex; justify-content: space-between; align-items: center;">
                                    <div style="flex: 1;">
                                        <h3>${module ? module.nom : 'Module ' + moduleId}</h3>
                                        <p style="color: var(--text-secondary); font-size: 0.9rem; margin-top: 0.5rem;">
                                            ${moduleNotes.length} note${moduleNotes.length > 1 ? 's' : ''}
                                        </p>
                                    </div>
                                    <div style="text-align: right;">
                                        <div style="font-size: 2.5rem; font-weight: 700; color: ${moyenne >= 10 ? 'var(--success-color)' : 'var(--error-color)'};">
                                            ${moyenne}
                                        </div>
                                        <div style="font-size: 0.9rem; color: var(--text-secondary);">sur 20</div>
                                    </div>
                                </div>
                                <div style="margin-top: 1rem; padding-top: 1rem; border-top: 1px solid var(--glass-border);">
                                    <div style="display: flex; gap: 0.5rem; flex-wrap: wrap;">
                                        ${moduleNotes.map(n => `
                                            <span style="padding: 0.25rem 0.75rem; background: var(--surface-dark); border-radius: 0.5rem; font-size: 0.85rem;">
                                                ${n.typeEvaluation}: ${n.valeur}
                                            </span>
                                        `).join('')}
                                    </div>
                                </div>
                            </div>
                        `;
        }).join('') || '<p style="color: var(--text-secondary);">Aucune moyenne disponible</p>'}
                </div>
            </div>
        `;
    }

    renderProfile(container) {
        if (!this.student) {
            container.innerHTML = `
                <div class="card">
                    <p style="color: var(--error-color);">Impossible de charger les informations du profil</p>
                </div>
            `;
            return;
        }

        container.innerHTML = `
            <div class="header">
                <h2>Mon Profil</h2>
            </div>

            <div class="card">
                <div style="display: flex; align-items: center; gap: 2rem; margin-bottom: 2rem;">
                    <div style="width: 100px; height: 100px; border-radius: 50%; background: linear-gradient(135deg, var(--primary-color), var(--secondary-color)); display: flex; align-items: center; justify-content: center; font-size: 3rem; font-weight: 700;">
                        ${this.student.prenom ? this.student.prenom.charAt(0).toUpperCase() : 'E'}
                    </div>
                    <div>
                        <h2>${this.student.nom} ${this.student.prenom}</h2>
                        <p style="color: var(--text-secondary); margin-top: 0.5rem;">
                            <i class="fas fa-envelope"></i> ${this.student.email}
                        </p>
                    </div>
                </div>

                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem;">
                    <div>
                        <label style="color: var(--text-secondary); font-size: 0.9rem; display: block; margin-bottom: 0.5rem;">Nom</label>
                        <div style="padding: 0.75rem; background: var(--surface-dark); border-radius: 0.5rem;">
                            ${this.student.nom}
                        </div>
                    </div>
                    <div>
                        <label style="color: var(--text-secondary); font-size: 0.9rem; display: block; margin-bottom: 0.5rem;">Prénom</label>
                        <div style="padding: 0.75rem; background: var(--surface-dark); border-radius: 0.5rem;">
                            ${this.student.prenom}
                        </div>
                    </div>
                    <div>
                        <label style="color: var(--text-secondary); font-size: 0.9rem; display: block; margin-bottom: 0.5rem;">Email</label>
                        <div style="padding: 0.75rem; background: var(--surface-dark); border-radius: 0.5rem;">
                            ${this.student.email}
                        </div>
                    </div>
                    <div>
                        <label style="color: var(--text-secondary); font-size: 0.9rem; display: block; margin-bottom: 0.5rem;">Niveau</label>
                        <div style="padding: 0.75rem; background: var(--surface-dark); border-radius: 0.5rem;">
                            ${this.student.niveau || 'Non spécifié'}
                        </div>
                    </div>
                </div>
            </div>

            <div class="card" style="margin-top: 2rem;">
                <h3>Statistiques Académiques</h3>
                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem; margin-top: 1rem;">
                    <div style="padding: 1rem; background: var(--surface-dark); border-radius: 0.5rem; text-align: center;">
                        <div style="font-size: 0.85rem; color: var(--text-secondary); margin-bottom: 0.5rem;">Notes Publiées</div>
                        <div style="font-size: 2rem; font-weight: 600;">${this.notes.length}</div>
                    </div>
                    <div style="padding: 1rem; background: var(--surface-dark); border-radius: 0.5rem; text-align: center;">
                        <div style="font-size: 0.85rem; color: var(--text-secondary); margin-bottom: 0.5rem;">Modules Suivis</div>
                        <div style="font-size: 2rem; font-weight: 600;">${new Set(this.notes.map(n => n.moduleId)).size}</div>
                    </div>
                    <div style="padding: 1rem; background: var(--surface-dark); border-radius: 0.5rem; text-align: center;">
                        <div style="font-size: 0.85rem; color: var(--text-secondary); margin-bottom: 0.5rem;">Moyenne Générale</div>
                        <div style="font-size: 2rem; font-weight: 600; color: ${this.moyennes.generale >= 10 ? 'var(--success-color)' : 'var(--error-color)'};">
                            ${this.moyennes.generale}
                        </div>
                    </div>
                </div>
            </div>
        `;
    }
}

export default StudentView;
