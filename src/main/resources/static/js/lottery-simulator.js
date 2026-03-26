// Lottery Simulator - Number Selection Logic

let selectedLotteryType = null;
let selectedNumbers = new Set();
let maxNumbers = 0;

// Lottery configuration
const lotteryConfig = {
    'LOTOFACIL': {
        maxNumbers: 25,
        recommended: 15,
        color: 'from-purple-500 to-purple-600'
    },
    'MEGASENA': {
        maxNumbers: 60,
        recommended: 6,
        color: 'from-green-500 to-green-600'
    }
};

// Select lottery type
function selectLotteryType(type) {
    selectedLotteryType = type;
    const config = lotteryConfig[type];
    maxNumbers = config.maxNumbers;
    
    // Update UI
    document.querySelectorAll('.lottery-type-btn').forEach(btn => {
        const check = btn.querySelector('.lottery-check');
        if (btn.id === `btn-${type}`) {
            btn.classList.add('border-primary-500', 'dark:border-primary-400', 'bg-primary-50', 'dark:bg-primary-900/20');
            btn.classList.remove('border-gray-300', 'dark:border-gray-600');
            check.classList.remove('hidden');
        } else {
            btn.classList.remove('border-primary-500', 'dark:border-primary-400', 'bg-primary-50', 'dark:bg-primary-900/20');
            btn.classList.add('border-gray-300', 'dark:border-gray-600');
            check.classList.add('hidden');
        }
    });
    
    // Clear previous selection
    selectedNumbers.clear();
    
    // Show number selection area
    document.getElementById('numberSelectionArea').classList.remove('hidden');
    
    // Generate number grid
    generateNumberGrid(maxNumbers);
    
    // Update display
    updateSelectedDisplay();
    
    // Scroll to number selection
    document.getElementById('numberSelectionArea').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// Generate number grid based on lottery type
function generateNumberGrid(max) {
    const grid = document.getElementById('numberGrid');
    grid.innerHTML = '';
    
    for (let i = 1; i <= max; i++) {
        const button = document.createElement('button');
        button.type = 'button';
        button.className = 'lottery-ball';
        button.innerHTML = `<span class="lottery-ball-number">${i}</span>`;
        button.onclick = () => toggleNumber(i, button);
        grid.appendChild(button);
    }
}

// Toggle number selection
function toggleNumber(number, button) {
    if (selectedNumbers.has(number)) {
        selectedNumbers.delete(number);
        button.classList.remove('lottery-ball-selected');
    } else {
        selectedNumbers.add(number);
        button.classList.add('lottery-ball-selected');
    }
    
    updateSelectedDisplay();
}

// Update selected numbers display
function updateSelectedDisplay() {
    const display = document.getElementById('selectedNumbersDisplay');
    const countDisplay = document.getElementById('selectedCount');
    const submitBtn = document.getElementById('submitBtn');
    
    // Update count
    countDisplay.textContent = selectedNumbers.size;
    
    // Update display
    if (selectedNumbers.size === 0) {
        display.innerHTML = '<span class="text-gray-400 dark:text-gray-500 italic">Nenhum número selecionado</span>';
        submitBtn.disabled = true;
    } else {
        const sortedNumbers = Array.from(selectedNumbers).sort((a, b) => a - b);
        display.innerHTML = sortedNumbers.map(num => 
            `<span class="lottery-ball-display">${num}</span>`
        ).join('');
        submitBtn.disabled = false;
    }
}

// Clear selection
function clearSelection() {
    selectedNumbers.clear();
    
    // Remove selection from all balls
    document.querySelectorAll('.lottery-ball').forEach(ball => {
        ball.classList.remove('lottery-ball-selected');
    });
    
    updateSelectedDisplay();
}

// Submit simulation
function submitSimulation() {
    if (selectedNumbers.size === 0) {
        alert('Por favor, selecione pelo menos um número!');
        return;
    }
    
    if (!selectedLotteryType) {
        alert('Por favor, selecione o tipo de loteria!');
        return;
    }
    
    // Show loading state
    const submitBtn = document.getElementById('submitBtn');
    const originalText = submitBtn.innerHTML;
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin mr-2"></i>Processando...';
    submitBtn.disabled = true;
    
    // Convert Set to Array and sort
    const numbersArray = Array.from(selectedNumbers).sort((a, b) => a - b);
    
    // Create query string with numbers as array parameters
    const queryParams = numbersArray.map(num => `numbers=${num}`).join('&');
    
    // Create form and submit with query string
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = `/web/simulate/${selectedLotteryType}?${queryParams}`;
    
    document.body.appendChild(form);
    form.submit();
}

// Keyboard shortcuts
document.addEventListener('keydown', (e) => {
    // Press 'C' to clear selection
    if (e.key === 'c' || e.key === 'C') {
        if (selectedNumbers.size > 0) {
            clearSelection();
        }
    }
    
    // Press 'Enter' to submit
    if (e.key === 'Enter') {
        if (selectedNumbers.size > 0 && selectedLotteryType) {
            submitSimulation();
        }
    }
});
