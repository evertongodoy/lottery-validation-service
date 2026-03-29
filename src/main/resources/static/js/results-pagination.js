// Results Pagination - Handle paginated display of lottery matches

let currentPage = 1;
let itemsPerPage = 10;
let totalItems = 0;
let totalPages = 1;
let minMatches = 0;

// Initialize pagination when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    const matchesContainer = document.getElementById('matchesContainer');
    
    if (!matchesContainer) {
        return; // No matches to paginate
    }
    
    const matchItems = document.querySelectorAll('.match-item');
    totalItems = matchItems.length;
    
    if (totalItems === 0) {
        return;
    }
    
    // Set total matches in the UI
    const totalMatchesElement = document.getElementById('totalMatches');
    if (totalMatchesElement) {
        totalMatchesElement.textContent = totalItems;
    }
    
    // Setup event listeners
    setupPaginationControls();
    
    // Initial render
    renderPage(1);
});

function setupPaginationControls() {
    // Items per page selector
    const itemsPerPageSelect = document.getElementById('itemsPerPage');
    if (itemsPerPageSelect) {
        itemsPerPageSelect.addEventListener('change', function() {
            itemsPerPage = parseInt(this.value);
            renderPage(1);
        });
    }
    
    // Min matches filter
    const minMatchesSelect = document.getElementById('minMatches');
    if (minMatchesSelect) {
        minMatchesSelect.addEventListener('change', function() {
            minMatches = parseInt(this.value);
            renderPage(1);
        });
    }
    
    // First page button
    const firstPageBtn = document.getElementById('firstPageBtn');
    if (firstPageBtn) {
        firstPageBtn.addEventListener('click', () => renderPage(1));
    }
    
    // Previous page button
    const prevPageBtn = document.getElementById('prevPageBtn');
    if (prevPageBtn) {
        prevPageBtn.addEventListener('click', () => renderPage(currentPage - 1));
    }
    
    // Next page button
    const nextPageBtn = document.getElementById('nextPageBtn');
    if (nextPageBtn) {
        nextPageBtn.addEventListener('click', () => renderPage(currentPage + 1));
    }
    
    // Last page button
    const lastPageBtn = document.getElementById('lastPageBtn');
    if (lastPageBtn) {
        lastPageBtn.addEventListener('click', () => renderPage(totalPages));
    }
}

function getFilteredItems() {
    const allItems = Array.from(document.querySelectorAll('.match-item'));
    
    if (minMatches === 0) {
        return allItems;
    }
    
    return allItems.filter(item => {
        const matches = parseInt(item.getAttribute('data-matches'));
        return matches === minMatches;
    });
}

function renderPage(pageNumber) {
    const filteredItems = getFilteredItems();
    totalItems = filteredItems.length;
    totalPages = Math.ceil(totalItems / itemsPerPage);
    
    // Validate page number
    if (pageNumber < 1) pageNumber = 1;
    if (totalPages > 0 && pageNumber > totalPages) pageNumber = totalPages;
    if (totalPages === 0) pageNumber = 1;
    
    currentPage = pageNumber;
    
    // Calculate start and end indices
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = Math.min(startIndex + itemsPerPage, totalItems);
    
    // Hide all items first
    const allItems = document.querySelectorAll('.match-item');
    allItems.forEach(item => {
        item.style.display = 'none';
    });
    
    // Show only filtered and paginated items
    filteredItems.forEach((item, index) => {
        const displayNumber = item.querySelector('.match-display-number');
        if (displayNumber) {
            displayNumber.textContent = index + 1;
        }
        
        if (index >= startIndex && index < endIndex) {
            item.style.display = 'block';
            item.classList.add('animate-fade-in');
        }
    });
    
    // Update range display
    updateRangeDisplay(totalItems > 0 ? startIndex + 1 : 0, endIndex);
    
    // Update total matches display
    const totalMatchesElement = document.getElementById('totalMatches');
    if (totalMatchesElement) {
        totalMatchesElement.textContent = totalItems;
    }
    
    // Update pagination buttons
    updatePaginationButtons();
    
    // Show "no results" message if no filtered items
    showNoResultsMessage(totalItems === 0);
    
    // Scroll to top of matches
    const matchesContainer = document.getElementById('matchesContainer');
    if (matchesContainer && totalItems > 0) {
        matchesContainer.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
}

function updateRangeDisplay(start, end) {
    const startElement = document.getElementById('currentRangeStart');
    const endElement = document.getElementById('currentRangeEnd');
    
    if (startElement) startElement.textContent = start;
    if (endElement) endElement.textContent = end;
}

function updatePaginationButtons() {
    // Update button states
    const firstPageBtn = document.getElementById('firstPageBtn');
    const prevPageBtn = document.getElementById('prevPageBtn');
    const nextPageBtn = document.getElementById('nextPageBtn');
    const lastPageBtn = document.getElementById('lastPageBtn');
    
    const noPagination = totalItems === 0 || totalPages <= 1;
    
    if (firstPageBtn) {
        firstPageBtn.disabled = currentPage === 1 || noPagination;
    }
    if (prevPageBtn) {
        prevPageBtn.disabled = currentPage === 1 || noPagination;
    }
    if (nextPageBtn) {
        nextPageBtn.disabled = currentPage === totalPages || noPagination;
    }
    if (lastPageBtn) {
        lastPageBtn.disabled = currentPage === totalPages || noPagination;
    }
    
    // Render page numbers
    renderPageNumbers();
}

function renderPageNumbers() {
    const paginationNumbers = document.getElementById('paginationNumbers');
    if (!paginationNumbers) return;
    
    paginationNumbers.innerHTML = '';
    
    if (totalPages <= 1) {
        return;
    }
    
    // Calculate which page numbers to show
    const maxButtons = 5;
    let startPage = Math.max(1, currentPage - Math.floor(maxButtons / 2));
    let endPage = Math.min(totalPages, startPage + maxButtons - 1);
    
    // Adjust start if we're near the end
    if (endPage - startPage < maxButtons - 1) {
        startPage = Math.max(1, endPage - maxButtons + 1);
    }
    
    // Add ellipsis at the beginning if needed
    if (startPage > 1) {
        const ellipsis = document.createElement('span');
        ellipsis.textContent = '...';
        ellipsis.className = 'px-2 text-gray-500 dark:text-gray-400';
        paginationNumbers.appendChild(ellipsis);
    }
    
    // Add page number buttons
    for (let i = startPage; i <= endPage; i++) {
        const button = document.createElement('button');
        button.textContent = i;
        button.className = `px-4 py-2 rounded-lg transition-colors ${
            i === currentPage 
                ? 'bg-primary-600 text-white font-bold' 
                : 'bg-gray-200 dark:bg-gray-700 hover:bg-gray-300 dark:hover:bg-gray-600 text-gray-800 dark:text-gray-200'
        }`;
        button.addEventListener('click', () => renderPage(i));
        paginationNumbers.appendChild(button);
    }
    
    // Add ellipsis at the end if needed
    if (endPage < totalPages) {
        const ellipsis = document.createElement('span');
        ellipsis.textContent = '...';
        ellipsis.className = 'px-2 text-gray-500 dark:text-gray-400';
        paginationNumbers.appendChild(ellipsis);
    }
}

function showNoResultsMessage(show) {
    let noResultsDiv = document.getElementById('noFilteredResults');
    
    if (show) {
        if (!noResultsDiv) {
            noResultsDiv = document.createElement('div');
            noResultsDiv.id = 'noFilteredResults';
            noResultsDiv.className = 'bg-yellow-50 dark:bg-yellow-900/20 border border-yellow-200 dark:border-yellow-800 rounded-xl p-8 text-center';
            noResultsDiv.innerHTML = `
                <i class="fas fa-filter text-5xl text-yellow-500 mb-4"></i>
                <h3 class="text-2xl font-bold text-yellow-800 dark:text-yellow-300 mb-2">
                    Nenhum resultado com esse filtro
                </h3>
                <p class="text-yellow-700 dark:text-yellow-400">
                    Nenhum sorteio encontrado com essa quantidade exata de acertos. Tente outra faixa.
                </p>
            `;
            
            const matchesContainer = document.getElementById('matchesContainer');
            if (matchesContainer) {
                matchesContainer.parentNode.insertBefore(noResultsDiv, matchesContainer);
            }
        }
        noResultsDiv.style.display = 'block';
    } else {
        if (noResultsDiv) {
            noResultsDiv.style.display = 'none';
        }
    }
}

// Keyboard navigation
document.addEventListener('keydown', function(e) {
    // Left arrow - previous page
    if (e.key === 'ArrowLeft' && currentPage > 1) {
        renderPage(currentPage - 1);
    }
    
    // Right arrow - next page
    if (e.key === 'ArrowRight' && currentPage < totalPages) {
        renderPage(currentPage + 1);
    }
});
