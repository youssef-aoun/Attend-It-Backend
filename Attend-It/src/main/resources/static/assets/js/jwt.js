function makeAuthenticatedRequest(apiEndpoint, jwtToken, type) {
    return new Promise((resolve, reject) => {
        fetch(apiEndpoint, {
            method: type,
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}

// Function to check if the user is authenticated and access a certain page
function checkAuthenticationAndAccessPage(pageUrl) {
    const jwtToken = localStorage.getItem('jwtToken');

    // If JWT token is not present, redirect user to login page
    if (!jwtToken) {
        window.location.href = '/users/login';
        return;
    }

    // Make an authenticated request to validate the token
    makeAuthenticatedRequest('/validateToken', jwtToken, 'GET')
        .then(() => {
            // If token is valid, allow access to the specified page
            window.location.href = pageUrl;
        })
        .catch(error => {
            console.error('Error:', error);
            // If token is invalid, redirect user to login page
            window.location.href = '/users/login';
        });
}
