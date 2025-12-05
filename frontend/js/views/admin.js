import Api from '../api.js';
import Auth from '../auth.js';

class AdminView {
    constructor(rootElement) {
        this.rootElement = rootElement;
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
                            <a href="#" class="nav-link active">
                                <i class="fas fa-chart-line"></i> Tableau de bord
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link">
                                <i class="fas fa-user-graduate"></i> Étudiants
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link">
                                <i class="fas fa-chalkboard-teacher"></i> Enseignants
                            </a>
                        </li>
                    </ul>
                    <div class="user-profile">
                        <div class="user-avatar">A</div>
                        <div>
                            <div style="font-weight: 500">Admin</div>
                            <div style="font-size: 0.8rem; color: var(--text-secondary)">${user.email}</div>
                        </div>
                        <button id="logoutBtn" class="btn btn-secondary" style="margin-left: auto; padding: 0.5rem;">
                            <i class="fas fa-sign-out-alt"></i>
                        </button>
                    </div>
                </aside>
                <main class="main-content">
                    <div class="header">
                        <h2>Tableau de bord</h2>
                    </div>

                    <div class="grid-cols-2" id="statsContainer">
                        <div class="card">
                            <h3>Chargement des statistiques...</h3>
                        </div>
                    </div>

                    <div class="card" style="margin-top: 2rem;">
                        <h3>Activité Récente</h3>
                        <p style="color: var(--text-secondary); margin-top: 1rem;">Aucune activité récente.</p>
                    </div>
                </main>
            </div>
        `;

        document.getElementById('logoutBtn').addEventListener('click', () => Auth.logout());
        this.loadStats();
    }

    async loadStats() {
        try {
            const stats = await Api.getStats();
            const container = document.getElementById('statsContainer');

            container.innerHTML = `
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Total Étudiants</h3>
                        <i class="fas fa-user-graduate" style="color: var(--primary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${stats.totalEtudiants || 0}</div>
                    <div style="color: var(--success-color); font-size: 0.9rem;">
                        <i class="fas fa-arrow-up"></i> Actifs
                    </div>
                </div>
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Total Enseignants</h3>
                        <i class="fas fa-chalkboard-teacher" style="color: var(--secondary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${stats.totalEnseignants || 0}</div>
                    <div style="color: var(--success-color); font-size: 0.9rem;">
                        <i class="fas fa-check"></i> Vérifiés
                    </div>
                </div>
            `;
        } catch (error) {
            console.error(error);
            document.getElementById('statsContainer').innerHTML = `
                <div class="card" style="grid-column: span 2; border-color: var(--error-color);">
                    <h3 style="color: var(--error-color)">Erreur</h3>
                    <p>Impossible de charger les statistiques.</p>
                </div>
            `;
        }
    }
}

export default AdminView;
