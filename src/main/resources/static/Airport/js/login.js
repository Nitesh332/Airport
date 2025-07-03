
        // Get elements for the login modal
        const openLoginModalButton = document.getElementById('openLoginModal');
        const loginModal = document.getElementById('loginModal');
        const closeLoginModalButton = document.getElementById('closeLoginModal');
        const modalLoginForm = document.getElementById('modalLoginForm');
        const modalLoginMessageDiv = document.getElementById('modalLoginMessage');

        // Function to open the login modal
        openLoginModalButton.addEventListener('click', () => {
            loginModal.classList.remove('hidden');
            modalLoginMessageDiv.textContent = ''; // Clear previous messages
            modalLoginMessageDiv.className = 'mt-4 text-center text-sm'; // Reset classes
        });

        // Function to close the login modal
        closeLoginModalButton.addEventListener('click', () => {
            loginModal.classList.add('hidden');
        });

        // Close modal if user clicks outside the modal content
        loginModal.addEventListener('click', (event) => {
            if (event.target === loginModal) {
                loginModal.classList.add('hidden');
            }
        });

        // Handle login form submission inside the modal
        modalLoginForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const username = document.getElementById('modalUsername').value;
            const password = document.getElementById('modalPassword').value;

            modalLoginMessageDiv.textContent = 'Logging in...';
            modalLoginMessageDiv.classList.add('text-gray-500');

            try {
                // Replace with your actual login API URL
                const apiUrl = 'http://localhost:8081/api/auth/register';

                const payload = {
                    username: username,
                    password: password
                };

                const response = await fetch(apiUrl, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(payload)
                });

                if (response.ok) {
                    const data = await response.json();
                    modalLoginMessageDiv.textContent = 'Login successful! Welcome!';
                    modalLoginMessageDiv.classList.remove('text-gray-500');
                    modalLoginMessageDiv.classList.add('text-green-600');
                    console.log('Login successful:', data);
                    // Optionally close modal or redirect after successful login
                    setTimeout(() => {
                        loginModal.classList.add('hidden');
                        modalLoginForm.reset();
                    }, 1500); // Close after 1.5 seconds
                } else {
                    const errorData = await response.json();
                    modalLoginMessageDiv.textContent = 'Username is already taken!';
                    modalLoginMessageDiv.classList.remove('text-gray-500');
                    modalLoginMessageDiv.classList.add('text-red-600');
                    console.error('Login failed:', response.status, errorData);
                }
            } catch (error) {
                modalLoginMessageDiv.textContent = 'A network error occurred. Please try again later.';
                modalLoginMessageDiv.classList.remove('text-gray-500');
                modalLoginMessageDiv.classList.add('text-red-600');
                console.error('Network or other error:', error);
            }
        });
    