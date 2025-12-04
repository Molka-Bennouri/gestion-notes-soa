const BASE_URL = 'http://localhost:8080/api';

class Api {
    static async request(endpoint, method = 'GET', body = null) {
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };

        const config = {
            method,
            headers,
        };

        if (body) {
            config.body = JSON.stringify(body);
        }

        try {
            const response = await fetch(`${BASE_URL}${endpoint}`, config);

            // Handle non-JSON responses (like simple strings)
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1) {
                const data = await response.json();
                if (!response.ok) {
                    throw new Error(data.message || 'Une erreur est survenue');
                }
                return data;
            } else {
                const text = await response.text();
                if (!response.ok) {
                    throw new Error(text || 'Une erreur est survenue');
                }
                return text;
            }
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    // Auth
    static async loginAdmin(credentials) {
        return this.request('/admin/login', 'POST', credentials);
    }

    static async loginEtudiant(credentials) {
        return this.request('/etudiants/login', 'POST', credentials);
    }

    static async loginEnseignant(credentials) {
        return this.request('/enseignants/login', 'POST', credentials);
    }

    // Admin Service
    static async getStats() {
        return this.request('/admin/statistiques');
    }

    static async createAdmin(admin) {
        return this.request('/admin/creer', 'POST', admin);
    }

    // Etudiant Service
    static async getAllEtudiants() {
        return this.request('/etudiants');
    }

    static async createEtudiant(etudiant) {
        return this.request('/etudiants', 'POST', etudiant);
    }

    static async getEtudiantById(id) {
        return this.request(`/etudiants/${id}`);
    }

    // Enseignant Service
    static async createEnseignant(enseignant) {
        return this.request('/enseignants/create', 'POST', enseignant);
    }

    static async addNote(note) {
        return this.request('/enseignants/notes/add', 'POST', note);
    }

    static async updateNote(id, note) {
        return this.request(`/enseignants/notes/update/${id}`, 'PUT', note);
    }

    static async getMoyenne(etudiantId, moduleId) {
        return this.request(`/enseignants/moyenne?etudiantId=${etudiantId}&moduleId=${moduleId}`);
    }

    // Module Service
    static async getAllModules() {
        return this.request('/modules/all');
    }

    static async createModule(module) {
        return this.request('/modules/add', 'POST', module);
    }

    // Note Service
    static async getAllNotes() {
        return this.request('/notes');
    }

    static async createNote(note) {
        return this.request('/notes', 'POST', note);
    }

    static async getNoteById(id) {
        return this.request(`/notes/${id}`);
    }

    static async getNotesByEtudiant(etudiantId) {
        return this.request(`/notes/etudiant/${etudiantId}`);
    }

    static async getPublishedNotesByEtudiant(etudiantId) {
        return this.request(`/notes/etudiant/${etudiantId}/publiees`);
    }

    static async getNotesByModule(moduleId) {
        return this.request(`/notes/module/${moduleId}`);
    }

    static async getNotesByEtudiantAndModule(etudiantId, moduleId) {
        return this.request(`/notes/etudiant/${etudiantId}/module/${moduleId}`);
    }

    static async modifierNote(id, note) {
        return this.request(`/notes/${id}`, 'PUT', note);
    }

    static async supprimerNote(id) {
        return this.request(`/notes/${id}`, 'DELETE');
    }

    static async publierNote(id) {
        return this.request(`/notes/${id}/publier`, 'PUT');
    }

    static async depublierNote(id) {
        return this.request(`/notes/${id}/depublier`, 'PUT');
    }

    static async getMoyenneEtudiantModule(etudiantId, moduleId) {
        return this.request(`/notes/moyenne/etudiant/${etudiantId}/module/${moduleId}`);
    }

    static async getMoyenneGenerale(etudiantId) {
        return this.request(`/notes/moyenne/etudiant/${etudiantId}`);
    }
}

export default Api;
