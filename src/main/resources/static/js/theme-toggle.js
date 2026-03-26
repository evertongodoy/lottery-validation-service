// Theme Toggle - Dark Mode functionality

// Check for saved theme preference or default to light mode
const currentTheme = localStorage.getItem('theme') || 'light';

// Apply theme on page load
if (currentTheme === 'dark') {
    document.documentElement.classList.add('dark');
    updateThemeIcons(true);
} else {
    document.documentElement.classList.remove('dark');
    updateThemeIcons(false);
}

// Theme toggle button handlers
const themeToggle = document.getElementById('themeToggle');
const themeToggleMobile = document.getElementById('themeToggleMobile');

if (themeToggle) {
    themeToggle.addEventListener('click', toggleTheme);
}

if (themeToggleMobile) {
    themeToggleMobile.addEventListener('click', toggleTheme);
}

function toggleTheme() {
    const isDark = document.documentElement.classList.toggle('dark');
    localStorage.setItem('theme', isDark ? 'dark' : 'light');
    updateThemeIcons(isDark);
}

function updateThemeIcons(isDark) {
    const themeIcon = document.getElementById('themeIcon');
    const themeIconMobile = document.getElementById('themeIconMobile');
    const themeLabelMobile = document.getElementById('themeLabelMobile');
    
    if (themeIcon) {
        if (isDark) {
            themeIcon.classList.remove('fa-moon');
            themeIcon.classList.add('fa-sun');
        } else {
            themeIcon.classList.remove('fa-sun');
            themeIcon.classList.add('fa-moon');
        }
    }
    
    if (themeIconMobile) {
        if (isDark) {
            themeIconMobile.classList.remove('fa-moon');
            themeIconMobile.classList.add('fa-sun');
        } else {
            themeIconMobile.classList.remove('fa-sun');
            themeIconMobile.classList.add('fa-moon');
        }
    }
    
    if (themeLabelMobile) {
        themeLabelMobile.textContent = isDark ? 'Modo Claro' : 'Modo Escuro';
    }
}
