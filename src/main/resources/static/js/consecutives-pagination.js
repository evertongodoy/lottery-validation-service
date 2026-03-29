// Consecutives Pagination and Interaction JavaScript

let currentPage = 1;
let itemsPerPage = 5;
let allNumbers = [];
let selectedSpecificNumber = null;
let selectedDrawNumber = null;

// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    // Get all number items
    allNumbers = Array.from(document.querySelectorAll('.number-item'));
    
    // Populate specific number dropdown based on lottery type
    populateSpecificNumberDropdown();
    
    // Populate draw number dropdown based on available draws
    populateDrawNumberDropdown();
    
    // Initialize pagination
    updatePagination();
    
    // Event listeners for items per page
    document.getElementById('itemsPerPage').addEventListener('change', function() {
        itemsPerPage = parseInt(this.value);
        selectedSpecificNumber = null;
        selectedDrawNumber = null;
        document.getElementById('specificNumber').value = '';
        document.getElementById('drawNumber').value = '';
        currentPage = 1;
        updatePagination();
        applyDrawFilter();
    });
    
    // Event listener for specific number filter
    document.getElementById('specificNumber').addEventListener('change', function() {
        selectedSpecificNumber = this.value ? parseInt(this.value) : null;
        selectedDrawNumber = null;
        document.getElementById('drawNumber').value = '';
        currentPage = 1;
        updatePagination();
        applyDrawFilter();
    });
    
    // Event listener for draw number filter
    document.getElementById('drawNumber').addEventListener('change', function() {
        selectedDrawNumber = this.value ? parseInt(this.value) : null;
        applyDrawFilter();
    });
    
    // Event listeners for pagination buttons
    document.getElementById('firstPageBtn').addEventListener('click', () => goToPage(1));
    document.getElementById('prevPageBtn').addEventListener('click', () => goToPage(currentPage - 1));
    document.getElementById('nextPageBtn').addEventListener('click', () => goToPage(currentPage + 1));
    document.getElementById('lastPageBtn').addEventListener('click', () => goToPage(getTotalPages()));
});

function populateSpecificNumberDropdown() {
    const select = document.getElementById('specificNumber');
    const maxNumber = LOTTERY_TYPE === 'LOTOFACIL' ? 25 : 60;
    
    // Clear existing options except the first one
    while (select.options.length > 1) {
        select.remove(1);
    }
    
    // Add number options
    for (let i = 1; i <= maxNumber; i++) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = `Número ${i}`;
        select.appendChild(option);
    }
}

function populateDrawNumberDropdown() {
    const select = document.getElementById('drawNumber');
    
    // Clear existing options except the first one
    while (select.options.length > 1) {
        select.remove(1);
    }
    
    // Find the maximum lottery number from all draws
    let maxDrawNumber = 0;
    
    allNumbers.forEach(item => {
        const drawItems = item.querySelectorAll('.draw-item');
        drawItems.forEach(drawItem => {
            const drawText = drawItem.querySelector('.text-xs').textContent;
            const drawNum = parseInt(drawText.replace('#', ''));
            if (drawNum > maxDrawNumber) {
                maxDrawNumber = drawNum;
            }
        });
    });
    
    // Add draw number options in descending order (most recent first)
    for (let i = maxDrawNumber; i >= 1; i--) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = `#${String(i).padStart(4, '0')}`;
        select.appendChild(option);
    }
}

function applyDrawFilter() {
    if (selectedDrawNumber === null) {
        // Show all draws
        allNumbers.forEach(item => {
            const drawItems = item.querySelectorAll('.draw-item');
            drawItems.forEach(drawItem => {
                drawItem.style.display = '';
            });
        });
    } else {
        // Filter draws by selected draw number
        allNumbers.forEach(item => {
            const drawItems = item.querySelectorAll('.draw-item');
            drawItems.forEach(drawItem => {
                const drawText = drawItem.querySelector('.text-xs').textContent;
                const drawNum = parseInt(drawText.replace('#', ''));
                
                if (drawNum === selectedDrawNumber) {
                    drawItem.style.display = '';
                } else {
                    drawItem.style.display = 'none';
                }
            });
        });
    }
}

function updatePagination() {
    // If specific number is selected, show only that number
    if (selectedSpecificNumber !== null) {
        showSpecificNumber(selectedSpecificNumber);
        return;
    }
    
    // Otherwise, use normal pagination
    const totalItems = allNumbers.length;
    const totalPages = getTotalPages();
    
    // Calculate range
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = Math.min(startIndex + itemsPerPage, totalItems);
    
    // Update display info
    document.getElementById('currentRangeStart').textContent = totalItems > 0 ? startIndex + 1 : 0;
    document.getElementById('currentRangeEnd').textContent = endIndex;
    
    // Hide all items
    allNumbers.forEach(item => {
        item.style.display = 'none';
    });
    
    // Show only items for current page
    for (let i = startIndex; i < endIndex; i++) {
        if (allNumbers[i]) {
            allNumbers[i].style.display = 'block';
            // Update position number
            const positionSpan = allNumbers[i].querySelector('.number-position');
            if (positionSpan) {
                positionSpan.textContent = i + 1;
            }
        }
    }
    
    // Update pagination buttons
    updatePaginationButtons(totalPages);
    
    // Update page numbers
    renderPageNumbers(totalPages);
}

function showSpecificNumber(number) {
    // Hide all items
    allNumbers.forEach(item => {
        item.style.display = 'none';
    });
    
    // Find and show the specific number
    let found = false;
    let position = 0;
    
    for (let i = 0; i < allNumbers.length; i++) {
        const item = allNumbers[i];
        // Get the number from the ball element
        const numberElement = item.querySelector('.w-16.h-16 span');
        if (numberElement && parseInt(numberElement.textContent) === number) {
            item.style.display = 'block';
            position = i + 1;
            found = true;
            
            // Update position display
            const positionSpan = item.querySelector('.number-position');
            if (positionSpan) {
                positionSpan.textContent = position;
            }
            break;
        }
    }
    
    // Update display info
    if (found) {
        document.getElementById('currentRangeStart').textContent = position;
        document.getElementById('currentRangeEnd').textContent = position;
    } else {
        document.getElementById('currentRangeStart').textContent = 0;
        document.getElementById('currentRangeEnd').textContent = 0;
    }
    
    // Hide pagination controls when showing specific number
    document.getElementById('firstPageBtn').disabled = true;
    document.getElementById('prevPageBtn').disabled = true;
    document.getElementById('nextPageBtn').disabled = true;
    document.getElementById('lastPageBtn').disabled = true;
    
    // Clear page numbers
    document.getElementById('paginationNumbers').innerHTML = '';
    
    // Scroll to the number if found
    if (found) {
        const container = document.getElementById('numbersContainer');
        if (container) {
            container.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    }
}

function getTotalPages() {
    return Math.ceil(allNumbers.length / itemsPerPage);
}

function goToPage(page) {
    const totalPages = getTotalPages();
    
    if (page < 1 || page > totalPages) {
        return;
    }
    
    currentPage = page;
    updatePagination();
    
    // Scroll to top of results
    const container = document.getElementById('numbersContainer');
    if (container) {
        container.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
}

function updatePaginationButtons(totalPages) {
    // First and Previous buttons
    document.getElementById('firstPageBtn').disabled = currentPage === 1;
    document.getElementById('prevPageBtn').disabled = currentPage === 1;
    
    // Next and Last buttons
    document.getElementById('nextPageBtn').disabled = currentPage === totalPages;
    document.getElementById('lastPageBtn').disabled = currentPage === totalPages;
}

function renderPageNumbers(totalPages) {
    const container = document.getElementById('paginationNumbers');
    container.innerHTML = '';
    
    // Determine which page numbers to show
    const maxButtons = 5;
    let startPage = Math.max(1, currentPage - Math.floor(maxButtons / 2));
    let endPage = Math.min(totalPages, startPage + maxButtons - 1);
    
    // Adjust start if we're near the end
    if (endPage - startPage < maxButtons - 1) {
        startPage = Math.max(1, endPage - maxButtons + 1);
    }
    
    // Add ellipsis before if needed
    if (startPage > 1) {
        const ellipsis = document.createElement('span');
        ellipsis.className = 'px-3 py-2 text-gray-500 dark:text-gray-400';
        ellipsis.textContent = '...';
        container.appendChild(ellipsis);
    }
    
    // Add page number buttons
    for (let i = startPage; i <= endPage; i++) {
        const btn = document.createElement('button');
        btn.className = 'px-4 py-2 rounded-lg transition-colors font-medium';
        
        if (i === currentPage) {
            btn.className += ' bg-purple-600 text-white';
        } else {
            btn.className += ' bg-purple-200 dark:bg-purple-700 hover:bg-purple-300 dark:hover:bg-purple-600 text-purple-900 dark:text-purple-200';
        }
        
        btn.textContent = i;
        btn.addEventListener('click', () => goToPage(i));
        container.appendChild(btn);
    }
    
    // Add ellipsis after if needed
    if (endPage < totalPages) {
        const ellipsis = document.createElement('span');
        ellipsis.className = 'px-3 py-2 text-gray-500 dark:text-gray-400';
        ellipsis.textContent = '...';
        container.appendChild(ellipsis);
    }
}

// Toggle draws visibility
function toggleDraws(index) {
    const drawsContent = document.getElementById('draws-' + index);
    const toggleIcon = drawsContent.previousElementSibling.querySelector('.toggle-icon');
    
    if (drawsContent.classList.contains('hidden')) {
        drawsContent.classList.remove('hidden');
        toggleIcon.style.transform = 'rotate(180deg)';
    } else {
        drawsContent.classList.add('hidden');
        toggleIcon.style.transform = 'rotate(0deg)';
    }
}
