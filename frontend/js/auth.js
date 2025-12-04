class Auth {
    static login(user, type) {
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('userType', type);
    }

    static logout() {
        localStorage.removeItem('user');
        localStorage.removeItem('userType');
        window.location.reload();
    }

    static getUser() {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    }

    static getUserType() {
        return localStorage.getItem('userType');
    }

    static isAuthenticated() {
        return !!this.getUser();
    }

    static requireAuth() {
        if (!this.isAuthenticated()) {
            // If not authenticated, we'll handle this in the router
            return false;
        }
        return true;
    }
}

export default Auth;
