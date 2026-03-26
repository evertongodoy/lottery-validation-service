// Mobile Menu Toggle

const mobileMenuBtn = document.getElementById('mobileMenuBtn');
const mobileMenu = document.getElementById('mobileMenu');

if (mobileMenuBtn && mobileMenu) {
    mobileMenuBtn.addEventListener('click', () => {
        mobileMenu.classList.toggle('hidden');
        
        // Change icon
        const icon = mobileMenuBtn.querySelector('i');
        if (mobileMenu.classList.contains('hidden')) {
            icon.classList.remove('fa-times');
            icon.classList.add('fa-bars');
        } else {
            icon.classList.remove('fa-bars');
            icon.classList.add('fa-times');
        }
    });
    
    // Close mobile menu when clicking outside
    document.addEventListener('click', (e) => {
        if (!mobileMenuBtn.contains(e.target) && !mobileMenu.contains(e.target)) {
            mobileMenu.classList.add('hidden');
            const icon = mobileMenuBtn.querySelector('i');
            icon.classList.remove('fa-times');
            icon.classList.add('fa-bars');
        }
    });
}

// Logout functionality (placeholder - implement according to your auth system)
const logoutBtn = document.getElementById('logoutBtn');
const logoutBtnMobile = document.getElementById('logoutBtnMobile');

if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
        // Implement logout logic here
        console.log('Logout clicked');
        // Example:
        // localStorage.removeItem('authToken');
        // window.location.href = '/web/home';
    });
}

if (logoutBtnMobile) {
    logoutBtnMobile.addEventListener('click', () => {
        // Implement logout logic here
        console.log('Logout clicked (mobile)');
        // Example:
        // localStorage.removeItem('authToken');
        // window.location.href = '/web/home';
    });
}
