import Api from '../api.js';
import Auth from '../auth.js';

class TeacherView {
    constructor(rootElement) {
        this.rootElement = rootElement;
        this.currentSection = 'dashboard';
        this.students = [];
        this.modules = [];
        this.notes = [];
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
                                <i class="fas fa-clipboard-list"></i> Gestion des Notes
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link" data-section="students">
                                <i class="fas fa-user-graduate"></i> Étudiants
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link" data-section="modules">
                                <i class="fas fa-book"></i> Modules
                            </a>
                        </li>
                    </ul>
                    <div class="user-profile">
                        <div class="user-avatar">E</div>
                        <div>
                            <div style="font-weight: 500">Enseignant</div>
                            <div style="font-size: 0.8rem; color: var(--text-secondary)">${user.email}</div>
                        </div>
                        <button id="logoutBtn" class="btn btn-secondary" style="margin-left: auto; padding: 0.5rem;">
                            <i class="fas fa-sign-out-alt"></i>
                        </button>
                    </div>
                </aside>
                <main class="main-content" id="mainContent">
                    <!-- Content will be loaded here -->
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
            [this.students, this.modules, this.notes] = await Promise.all([

                Api.getAllEtudiants(),
                Api.getAllModules(),
                Api.getAllNotes()
            ]);
        } catch (error) {
            console.error('Error loading initial data:', error);
        }
    }

    loadSection(section) {
        const mainContent = document.getElementById('mainContent');

        switch (section) {
            case 'dashboard':
                this.renderDashboard(mainContent);
                break;
            case 'notes':
                this.renderNotesManagement(mainContent);
                break;
            case 'students':
                this.renderStudents(mainContent);
                break;
            case 'modules':
                this.renderModules(mainContent);
                break;
        }
    }

    renderDashboard(container) {
        const totalNotes = this.notes.length;
        const publishedNotes = this.notes.filter(n => n.publiee).length;
        const averageGrade = this.notes.length > 0
            ? (this.notes.reduce((sum, n) => sum + n.valeur, 0) / this.notes.length).toFixed(2)
            : 0;

        container.innerHTML = `
            <div class="header">
                <h2>Tableau de bord</h2>
            </div>

            <div class="grid-cols-2" style="grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));">
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Total Étudiants</h3>
                        <i class="fas fa-user-graduate" style="color: var(--primary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${this.students.length}</div>
                    <div style="color: var(--success-color); font-size: 0.9rem;">
                        <i class="fas fa-check"></i> Actifs
                    </div>
                </div>
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Total Modules</h3>
                        <i class="fas fa-book" style="color: var(--secondary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${this.modules.length}</div>
                    <div style="color: var(--success-color); font-size: 0.9rem;">
                        <i class="fas fa-check"></i> Disponibles
                    </div>
                </div>
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Total Notes</h3>
                        <i class="fas fa-clipboard-list" style="color: var(--primary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${totalNotes}</div>
                    <div style="color: var(--text-secondary); font-size: 0.9rem;">
                        <i class="fas fa-eye"></i> ${publishedNotes} publiées
                    </div>
                </div>
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3 style="color: var(--text-secondary); font-size: 1rem;">Moyenne Générale</h3>
                        <i class="fas fa-chart-bar" style="color: var(--secondary-color); font-size: 1.5rem;"></i>
                    </div>
                    <div style="font-size: 2.5rem; font-weight: 700;">${averageGrade}</div>
                    <div style="color: var(--text-secondary); font-size: 0.9rem;">
                        <i class="fas fa-calculator"></i> Sur 20
                    </div>
                </div>
            </div>

            <div class="card" style="margin-top: 2rem;">
                <h3>Notes Récentes</h3>
                <div class="table-container">
                    ${this.renderNotesTable(this.notes.slice(0, 5))}
                </div>
            </div>
        `;
    }

    renderNotesManagement(container) {
        container.innerHTML = `
            <div class="header">
                <h2>Gestion des Notes</h2>
                <button class="btn btn-primary" id="addNoteBtn">
                    <i class="fas fa-plus"></i> Ajouter une note
                </button>
            </div>

            <div class="card">
                <div style="display: flex; gap: 1rem; margin-bottom: 1rem;">
                    <div class="input-group" style="flex: 1; margin-bottom: 0;">
                        <select id="filterStudent" class="input-control">
                            <option value="">Tous les étudiants</option>
                            ${this.students.map(s => `<option value="${s.id}">${s.nom} ${s.prenom}</option>`).join('')}
                        </select>
                    </div>
                    <div class="input-group" style="flex: 1; margin-bottom: 0;">
                        <select id="filterModule" class="input-control">
                            <option value="">Tous les modules</option>
                            ${this.modules.map(m => `<option value="${m.id}">${m.nom}</option>`).join('')}
                        </select>
                    </div>
                </div>
                <div class="table-container">
                    ${this.renderNotesTable(this.notes)}
                </div>
            </div>

            <!-- Modal for adding/editing notes -->
            <div id="noteModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.7); z-index: 1000; align-items: center; justify-content: center;">
                <div class="card" style="max-width: 500px; width: 90%; max-height: 90vh; overflow-y: auto;">
                    <h3 id="modalTitle">Ajouter une note</h3>
                    <form id="noteForm">
                        <div class="input-group">
                            <label>Étudiant</label>
                            <select id="noteEtudiant" class="input-control" required>
                                <option value="">Sélectionner un étudiant</option>
                                ${this.students.map(s => `<option value="${s.id}">${s.nom} ${s.prenom}</option>`).join('')}
                            </select>
                        </div>
                        <div class="input-group">
                            <label>Module</label>
                            <select id="noteModule" class="input-control" required>
                                <option value="">Sélectionner un module</option>
                                ${this.modules.map(m => `<option value="${m.id}">${m.nom}</option>`).join('')}
                            </select>
                        </div>
                        <div class="input-group">
                            <label>Type d'évaluation</label>
                            <select id="noteType" class="input-control" required>
                                <option value="DS">DS - Devoir Surveillé</option>
                                <option value="TP">TP - Travaux Pratiques</option>
                                <option value="EXAMEN">Examen</option>
                                <option value="PROJET">Projet</option>
                            </select>
                        </div>
                        <div class="input-group">
                            <label>Note (sur 20)</label>
                            <input type="number" id="noteValeur" class="input-control" min="0" max="20" step="0.5" required>
                        </div>
                        <div class="input-group">
                            <label>Coefficient</label>
                            <input type="number" id="noteCoefficient" class="input-control" min="1" max="5" step="0.5" value="1" required>
                        </div>
                        <div style="display: flex; gap: 1rem;">
                            <button type="submit" class="btn btn-primary" style="flex: 1;">
                                <i class="fas fa-save"></i> Enregistrer
                            </button>
                            <button type="button" class="btn btn-secondary" id="cancelNoteBtn" style="flex: 1;">
                                <i class="fas fa-times"></i> Annuler
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        `;

        // Event listeners
        document.getElementById('addNoteBtn').addEventListener('click', () => this.showNoteModal());
        document.getElementById('filterStudent').addEventListener('change', (e) => this.filterNotes(e.target.value, document.getElementById('filterModule').value));
        document.getElementById('filterModule').addEventListener('change', (e) => this.filterNotes(document.getElementById('filterStudent').value, e.target.value));
    }

    renderNotesTable(notes) {
        if (notes.length === 0) {
            return '<p style="color: var(--text-secondary); text-align: center; padding: 2rem;">Aucune note disponible</p>';
        }

        return `
            <table>
                <thead>
                    <tr>
                        <th>Étudiant</th>
                        <th>Module</th>
                        <th>Type</th>
                        <th>Note</th>
                        <th>Coef.</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    ${notes.map(note => {
            const student = this.students.find(s => s.id === note.etudiantId);
            const module = this.modules.find(m => m.id === note.moduleId);
            return `
                            <tr>
                                <td>${student ? `${student.nom} ${student.prenom}` : 'N/A'}</td>
                                <td>${module ? module.nom : 'N/A'}</td>
                                <td>${note.typeEvaluation}</td>
                                <td><strong>${note.valeur}/20</strong></td>
                                <td>${note.coefficient}</td>
                                <td>
                                    <span style="padding: 0.25rem 0.75rem; border-radius: 1rem; font-size: 0.85rem; background: ${note.publiee ? 'var(--success-color)' : 'var(--surface-light)'}; color: white;">
                                        ${note.publiee ? 'Publiée' : 'Non publiée'}
                                    </span>
                                </td>
                                <td>
                                    <div style="display: flex; gap: 0.5rem;">
                                        <button class="btn btn-secondary" style="padding: 0.5rem;" onclick="teacherView.togglePublish(${note.id}, ${note.publiee})" title="${note.publiee ? 'Dépublier' : 'Publier'}">
                                            <i class="fas fa-${note.publiee ? 'eye-slash' : 'eye'}"></i>
                                        </button>
                                        <button class="btn btn-secondary" style="padding: 0.5rem;" onclick="teacherView.editNote(${note.id})" title="Modifier">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn btn-danger" style="padding: 0.5rem;" onclick="teacherView.deleteNote(${note.id})" title="Supprimer">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        `;
        }).join('')}
                </tbody>
            </table>
        `;
    }

    renderStudents(container) {
        container.innerHTML = `
            <div class="header">
                <h2>Liste des Étudiants</h2>
            </div>

            <div class="card">
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Nom</th>
                                <th>Prénom</th>
                                <th>Email</th>
                                <th>Niveau</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${this.students.map(student => `
                                <tr>
                                    <td>${student.nom}</td>
                                    <td>${student.prenom}</td>
                                    <td>${student.email}</td>
                                    <td>${student.niveau || 'N/A'}</td>
                                    <td>
                                        <button class="btn btn-secondary" style="padding: 0.5rem;" onclick="teacherView.viewStudentNotes(${student.id})" title="Voir les notes">
                                            <i class="fas fa-eye"></i> Notes
                                        </button>
                                    </td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </div>
            </div>
        `;
    }

    renderModules(container) {
        container.innerHTML = `
            <div class="header">
                <h2>Liste des Modules</h2>
            </div>

            <div class="grid-cols-2" style="grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));">
                ${this.modules.map(module => {
            const moduleNotes = this.notes.filter(n => n.moduleId === module.id);
            const avgNote = moduleNotes.length > 0
                ? (moduleNotes.reduce((sum, n) => sum + n.valeur, 0) / moduleNotes.length).toFixed(2)
                : 'N/A';

            return `
                        <div class="card">
                                                    <h3>${module.nomModule}</h3>

                                                    <div style="margin: 1rem 0;">
                                                        <div style="color: var(--text-secondary); font-size: 0.9rem;">
                                                            Niveau : <strong>${module.niveau}</strong>
                                                        </div>
                                                        <div style="color: var(--text-secondary); font-size: 0.9rem;">
                                                            Coefficient : <strong>${module.coefficient}</strong>
                                                        </div>
                                                    </div>

                                                    <div style="display: flex; justify-content: space-between; margin-top: 1rem; padding-top: 1rem; border-top: 1px solid var(--glass-border);">
                                                        <div>
                                                            <div style="font-size: 0.85rem; color: var(--text-secondary);">Notes Enregistrées</div>
                                                            <div style="font-size: 1.5rem; font-weight: 600;">${moduleNotes.length}</div>
                                                        </div>
                                                        <div>
                                                            <div style="font-size: 0.85rem; color: var(--text-secondary);">Moyenne</div>
                                                            <div style="font-size: 1.5rem; font-weight: 600;">${avgNote}</div>
                                                        </div>
                                                    </div>
                                                </div>
                    `;
        }).join('')}
            </div>
        `;
    }

    showNoteModal(noteId = null) {
        const modal = document.getElementById('noteModal');
        const form = document.getElementById('noteForm');
        const title = document.getElementById('modalTitle');

        if (noteId) {
            const note = this.notes.find(n => n.id === noteId);
            title.textContent = 'Modifier la note';
            document.getElementById('noteEtudiant').value = note.etudiantId;
            document.getElementById('noteModule').value = note.moduleId;
            document.getElementById('noteType').value = note.typeEvaluation;
            document.getElementById('noteValeur').value = note.valeur;
            document.getElementById('noteCoefficient').value = note.coefficient;
            form.dataset.noteId = noteId;
        } else {
            title.textContent = 'Ajouter une note';
            form.reset();
            delete form.dataset.noteId;
        }

        modal.style.display = 'flex';

        // Event listeners
        const handleSubmit = async (e) => {
            e.preventDefault();
            await this.saveNote();
            form.removeEventListener('submit', handleSubmit);
        };

        const handleCancel = () => {
            modal.style.display = 'none';
            cancelBtn.removeEventListener('click', handleCancel);
        };

        form.addEventListener('submit', handleSubmit);
        const cancelBtn = document.getElementById('cancelNoteBtn');
        cancelBtn.addEventListener('click', handleCancel);
    }

    async saveNote() {
        const form = document.getElementById('noteForm');
        const noteData = {
            etudiantId: parseInt(document.getElementById('noteEtudiant').value),
            moduleId: parseInt(document.getElementById('noteModule').value),
            typeEvaluation: document.getElementById('noteType').value,
            valeur: parseFloat(document.getElementById('noteValeur').value),
            coefficient: parseFloat(document.getElementById('noteCoefficient').value)
        };

        try {
            if (form.dataset.noteId) {
                await Api.modifierNote(parseInt(form.dataset.noteId), noteData);
            } else {
                await Api.createNote(noteData);
            }

            await this.loadInitialData();
            document.getElementById('noteModal').style.display = 'none';
            this.loadSection(this.currentSection);
        } catch (error) {
            alert('Erreur: ' + error.message);
        }
    }

    async togglePublish(noteId, isPublished) {
        try {
            if (isPublished) {
                await Api.depublierNote(noteId);
            } else {
                await Api.publierNote(noteId);
            }
            await this.loadInitialData();
            this.loadSection(this.currentSection);
        } catch (error) {
            alert('Erreur: ' + error.message);
        }
    }

    editNote(noteId) {
        this.showNoteModal(noteId);
    }

    async deleteNote(noteId) {
        if (confirm('Êtes-vous sûr de vouloir supprimer cette note ?')) {
            try {
                await Api.supprimerNote(noteId);
                await this.loadInitialData();
                this.loadSection(this.currentSection);
            } catch (error) {
                alert('Erreur: ' + error.message);
            }
        }
    }

    filterNotes(studentId, moduleId) {
        let filtered = [...this.notes];

        if (studentId) {
            filtered = filtered.filter(n => n.etudiantId === parseInt(studentId));
        }
        if (moduleId) {
            filtered = filtered.filter(n => n.moduleId === parseInt(moduleId));
        }

        const tableContainer = document.querySelector('.table-container');
        if (tableContainer) {
            tableContainer.innerHTML = this.renderNotesTable(filtered);
        }
    }

    async viewStudentNotes(studentId) {
        this.currentSection = 'notes';
        document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
        document.querySelector('[data-section="notes"]').classList.add('active');
        this.loadSection('notes');

        setTimeout(() => {
            document.getElementById('filterStudent').value = studentId;
            this.filterNotes(studentId, '');
        }, 100);
    }
}

// Make it globally accessible for onclick handlers
window.teacherView = null;

export default TeacherView;
