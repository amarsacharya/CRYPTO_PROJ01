const API_BASE_URL = 'http://localhost:8080/api';
let authToken = localStorage.getItem('authToken');
let currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
let selectedPortfolioId = null;

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    if (authToken && currentUser.username) {
        showDashboard();
    } else {
        showLogin();
    }

    // Set up form event listeners
    document.getElementById('loginFormElement').addEventListener('submit', handleLogin);
    document.getElementById('registerFormElement').addEventListener('submit', handleRegister);
    document.getElementById('createPortfolioForm').addEventListener('submit', handleCreatePortfolio);
    document.getElementById('addHoldingForm').addEventListener('submit', handleAddHolding);
});

// Authentication functions
async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(`${API_BASE_URL}/auth/signin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (response.ok) {
            const data = await response.json();
            authToken = data.token;
            currentUser = { username: data.username, email: data.email };
            
            localStorage.setItem('authToken', authToken);
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            
            showDashboard();
        } else {
            const error = await response.text();
            alert('Login failed: ' + error);
        }
    } catch (error) {
        alert('Login failed: ' + error.message);
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const username = document.getElementById('registerUsername').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;

    try {
        const response = await fetch(`${API_BASE_URL}/auth/signup`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, email, password }),
        });

        if (response.ok) {
            alert('Registration successful! Please login.');
            showLogin();
        } else {
            const error = await response.text();
            alert('Registration failed: ' + error);
        }
    } catch (error) {
        alert('Registration failed: ' + error.message);
    }
}

function logout() {
    authToken = null;
    currentUser = {};
    localStorage.removeItem('authToken');
    localStorage.removeItem('currentUser');
    showLogin();
}

// UI Navigation functions
function showLogin() {
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('registerForm').style.display = 'none';
    document.getElementById('dashboard').style.display = 'none';
}

function showRegister() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registerForm').style.display = 'block';
    document.getElementById('dashboard').style.display = 'none';
}

function showDashboard() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registerForm').style.display = 'none';
    document.getElementById('dashboard').style.display = 'block';
    
    document.getElementById('welcomeMessage').textContent = `Welcome, ${currentUser.username}!`;
    
    loadPortfolios();
    loadCryptocurrencies();
}

// Portfolio functions
async function loadPortfolios() {
    try {
        const response = await fetch(`${API_BASE_URL}/portfolios`, {
            headers: {
                'Authorization': `Bearer ${authToken}`,
            },
        });

        if (response.ok) {
            const portfolios = await response.json();
            displayPortfolios(portfolios);
        } else {
            console.error('Failed to load portfolios');
        }
    } catch (error) {
        console.error('Error loading portfolios:', error);
    }
}

function displayPortfolios(portfolios) {
    const portfoliosList = document.getElementById('portfoliosList');
    portfoliosList.innerHTML = '';

    portfolios.forEach(portfolio => {
        const portfolioCard = document.createElement('div');
        portfolioCard.className = 'card';
        portfolioCard.innerHTML = `
            <h4>${portfolio.name}</h4>
            <p>${portfolio.description || 'No description'}</p>
            <p>Total Value: $${(portfolio.totalValue || 0).toFixed(2)}</p>
            <p class="${(portfolio.totalProfitLoss || 0) >= 0 ? 'profit' : 'loss'}">
                P&L: $${(portfolio.totalProfitLoss || 0).toFixed(2)}
            </p>
            <div class="card-actions">
                <button onclick="viewPortfolioHoldings(${portfolio.id})">View Holdings</button>
                <button onclick="showAddHoldingModal(${portfolio.id})">Add Holding</button>
                <button onclick="deletePortfolio(${portfolio.id})">Delete</button>
            </div>
        `;
        portfoliosList.appendChild(portfolioCard);
    });
}

async function handleCreatePortfolio(e) {
    e.preventDefault();
    const name = document.getElementById('portfolioName').value;
    const description = document.getElementById('portfolioDescription').value;

    try {
        const response = await fetch(`${API_BASE_URL}/portfolios`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${authToken}`,
            },
            body: JSON.stringify({ name, description }),
        });

        if (response.ok) {
            closeCreatePortfolioModal();
            loadPortfolios();
        } else {
            const error = await response.text();
            alert('Failed to create portfolio: ' + error);
        }
    } catch (error) {
        alert('Error creating portfolio: ' + error.message);
    }
}

async function deletePortfolio(portfolioId) {
    if (confirm('Are you sure you want to delete this portfolio?')) {
        try {
            const response = await fetch(`${API_BASE_URL}/portfolios/${portfolioId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                },
            });

            if (response.ok) {
                loadPortfolios();
            } else {
                alert('Failed to delete portfolio');
            }
        } catch (error) {
            alert('Error deleting portfolio: ' + error.message);
        }
    }
}

// Holdings functions
async function viewPortfolioHoldings(portfolioId) {
    selectedPortfolioId = portfolioId;
    try {
        const response = await fetch(`${API_BASE_URL}/holdings/portfolio/${portfolioId}`, {
            headers: {
                'Authorization': `Bearer ${authToken}`,
            },
        });

        if (response.ok) {
            const holdings = await response.json();
            displayHoldings(holdings);
        } else {
            console.error('Failed to load holdings');
        }
    } catch (error) {
        console.error('Error loading holdings:', error);
    }
}

function displayHoldings(holdings) {
    const holdingsList = document.getElementById('holdingsList');
    holdingsList.innerHTML = '<h4>Portfolio Holdings:</h4>';

    if (holdings.length === 0) {
        holdingsList.innerHTML += '<p>No holdings found.</p>';
        return;
    }

    holdings.forEach(holding => {
        const holdingCard = document.createElement('div');
        holdingCard.className = 'card';
        holdingCard.innerHTML = `
            <h4>${holding.cryptocurrencySymbol} - ${holding.cryptocurrencyName}</h4>
            <p>Quantity: ${holding.quantity}</p>
            <p>Purchase Price: $${holding.purchasePrice.toFixed(2)}</p>
            <p>Current Price: $${holding.cryptocurrencyCurrentPrice.toFixed(2)}</p>
            <p>Current Value: $${(holding.currentValue || 0).toFixed(2)}</p>
            <p class="${(holding.profitLoss || 0) >= 0 ? 'profit' : 'loss'}">
                P&L: $${(holding.profitLoss || 0).toFixed(2)}
            </p>
            <div class="card-actions">
                <button onclick="deleteHolding(${holding.id})">Delete</button>
            </div>
        `;
        holdingsList.appendChild(holdingCard);
    });
}

async function handleAddHolding(e) {
    e.preventDefault();
    const cryptocurrencyId = document.getElementById('holdingCrypto').value;
    const quantity = document.getElementById('holdingQuantity').value;
    const purchasePrice = document.getElementById('holdingPrice').value;

    try {
        const response = await fetch(`${API_BASE_URL}/holdings`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${authToken}`,
            },
            body: JSON.stringify({
                portfolioId: selectedPortfolioId,
                cryptocurrencyId: parseInt(cryptocurrencyId),
                quantity: parseFloat(quantity),
                purchasePrice: parseFloat(purchasePrice),
            }),
        });

        if (response.ok) {
            closeAddHoldingModal();
            loadPortfolios();
            if (selectedPortfolioId) {
                viewPortfolioHoldings(selectedPortfolioId);
            }
        } else {
            const error = await response.text();
            alert('Failed to add holding: ' + error);
        }
    } catch (error) {
        alert('Error adding holding: ' + error.message);
    }
}

async function deleteHolding(holdingId) {
    if (confirm('Are you sure you want to delete this holding?')) {
        try {
            const response = await fetch(`${API_BASE_URL}/holdings/${holdingId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                },
            });

            if (response.ok) {
                loadPortfolios();
                if (selectedPortfolioId) {
                    viewPortfolioHoldings(selectedPortfolioId);
                }
            } else {
                alert('Failed to delete holding');
            }
        } catch (error) {
            alert('Error deleting holding: ' + error.message);
        }
    }
}

// Cryptocurrency functions
async function loadCryptocurrencies() {
    try {
        const response = await fetch(`${API_BASE_URL}/cryptocurrencies`);

        if (response.ok) {
            const cryptocurrencies = await response.json();
            displayCryptocurrencies(cryptocurrencies);
            populateCryptoSelect(cryptocurrencies);
        } else {
            console.error('Failed to load cryptocurrencies');
        }
    } catch (error) {
        console.error('Error loading cryptocurrencies:', error);
    }
}

function displayCryptocurrencies(cryptocurrencies) {
    const cryptoList = document.getElementById('cryptoList');
    cryptoList.innerHTML = '';

    cryptocurrencies.forEach(crypto => {
        const cryptoCard = document.createElement('div');
        cryptoCard.className = 'card';
        cryptoCard.innerHTML = `
            <h4>${crypto.symbol} - ${crypto.name}</h4>
            <p>Current Price: $${crypto.currentPrice.toFixed(2)}</p>
            <p class="${(crypto.priceChange24h || 0) >= 0 ? 'profit' : 'loss'}">
                24h Change: $${(crypto.priceChange24h || 0).toFixed(2)}
            </p>
            <p>Last Updated: ${new Date(crypto.lastUpdated).toLocaleString()}</p>
        `;
        cryptoList.appendChild(cryptoCard);
    });
}

function populateCryptoSelect(cryptocurrencies) {
    const select = document.getElementById('holdingCrypto');
    select.innerHTML = '<option value="">Select Cryptocurrency</option>';
    
    cryptocurrencies.forEach(crypto => {
        const option = document.createElement('option');
        option.value = crypto.id;
        option.textContent = `${crypto.symbol} - ${crypto.name} ($${crypto.currentPrice.toFixed(2)})`;
        select.appendChild(option);
    });
}

// Modal functions
function showCreatePortfolioModal() {
    document.getElementById('createPortfolioModal').style.display = 'flex';
}

function closeCreatePortfolioModal() {
    document.getElementById('createPortfolioModal').style.display = 'none';
    document.getElementById('createPortfolioForm').reset();
}

function showAddHoldingModal(portfolioId) {
    selectedPortfolioId = portfolioId;
    document.getElementById('addHoldingModal').style.display = 'flex';
}

function closeAddHoldingModal() {
    document.getElementById('addHoldingModal').style.display = 'none';
    document.getElementById('addHoldingForm').reset();
}

// Auto-refresh data every 30 seconds
setInterval(() => {
    if (authToken) {
        loadPortfolios();
        loadCryptocurrencies();
        if (selectedPortfolioId) {
            viewPortfolioHoldings(selectedPortfolioId);
        }
    }
}, 30000);