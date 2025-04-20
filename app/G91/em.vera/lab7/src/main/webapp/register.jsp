<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Form</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .form-container:hover {
            transform: translateY(-2px);
            transition: all 0.3s ease;
        }

        .input-field {
            transition: all 0.2s ease;
        }

        .input-field:focus {
            outline: none;
            box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.3);
        }

        .submit-button {
            transition: all 0.3s ease;
        }

        .submit-button:hover {
            transform: scale(1.02);
        }
    </style>
</head>
<body class="min-h-screen bg-gradient-to-br from-indigo-50 via-purple-50 to-pink-50 flex items-center justify-center p-6">
<div class="w-full max-w-md form-container">
    <div class="bg-white rounded-2xl shadow-lg overflow-hidden">
        <div class="bg-gradient-to-r from-violet-500 to-fuchsia-500 p-6">
            <h2 class="text-white text-2xl font-bold text-center">Create Account</h2>
            <p class="text-white/80 text-center mt-2">Join our community today</p>
        </div>

        <div class="p-8">
            <form id="registrationForm" class="space-y-6">
                <!-- Name Field -->
                <div class="relative">
                    <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none text-gray-400">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/>
                            <circle cx="12" cy="7" r="4"/>
                        </svg>
                    </div>
                    <input
                            type="text"
                            id="name"
                            name="name"
                            class="input-field block w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-violet-500 focus:border-violet-500 bg-gray-50"
                            placeholder="Your Name"
                            required
                    />
                    <p class="mt-1 text-sm text-red-500 hidden" id="nameError"></p>
                </div>

                <!-- Email Field -->
                <div class="relative">
                    <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none text-gray-400">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <rect width="20" height="16" x="2" y="4" rx="2"/>
                            <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"/>
                        </svg>
                    </div>
                    <input
                            type="email"
                            id="email"
                            name="email"
                            class="input-field block w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-violet-500 focus:border-violet-500 bg-gray-50"
                            placeholder="Email Address"
                            required
                    />
                    <p class="mt-1 text-sm text-red-500 hidden" id="emailError"></p>
                </div>

                <!-- Password Field -->
                <div class="relative">
                    <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none text-gray-400">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <rect width="18" height="11" x="3" y="11" rx="2" ry="2"/>
                            <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                        </svg>
                    </div>
                    <input
                            type="password"
                            id="password"
                            name="password"
                            class="input-field block w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-violet-500 focus:border-violet-500 bg-gray-50"
                            placeholder="Create Password"
                            required
                    />
                    <button
                            type="button"
                            id="togglePassword"
                            class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-500 hover:text-violet-700 transition-colors"
                    >
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" id="passwordIcon">
                            <path d="M12 5c-7.2 0-9 6-9 6s1.8 6 9 6 9-6 9-6-1.8-6-9-6z"/>
                            <circle cx="12" cy="11" r="3"/>
                        </svg>
                    </button>
                    <p class="mt-1 text-sm text-red-500 hidden" id="passwordError"></p>
                </div>

                <!-- Submit Button -->
                <button
                        type="submit"
                        class="submit-button w-full py-3 px-4 bg-gradient-to-r from-violet-600 to-fuchsia-600 text-white font-medium rounded-lg shadow-md hover:from-violet-700 hover:to-fuchsia-700 focus:outline-none focus:ring-2 focus:ring-violet-500 focus:ring-opacity-50"
                >
                    Register
                </button>
            </form>

            <!-- Login Link -->
            <div class="mt-6 text-center">
                <p class="text-gray-600">
                    Already have an account?
                    <a href="#" class="text-violet-600 hover:text-violet-800 font-medium transition-colors">
                        Sign In
                    </a>
                </p>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('registrationForm');
        const togglePassword = document.getElementById('togglePassword');
        const passwordInput = document.getElementById('password');
        const passwordIcon = document.getElementById('passwordIcon');

        // Toggle password visibility
        togglePassword.addEventListener('click', function() {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);

            // Update icon
            if (type === 'password') {
                passwordIcon.innerHTML = '<path d="M12 5c-7.2 0-9 6-9 6s1.8 6 9 6 9-6 9-6-1.8-6-9-6z"/><circle cx="12" cy="11" r="3"/>';
            } else {
                passwordIcon.innerHTML = '<path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/>';
            }
        });

        // Form validation
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            let isValid = true;

            // Reset errors
            document.querySelectorAll('.text-red-500').forEach(error => error.classList.add('hidden'));
            document.querySelectorAll('input').forEach(input => input.classList.remove('border-red-500'));

            // Name validation
            const name = document.getElementById('name');
            if (!name.value.trim()) {
                showError('name', 'Name is required');
                isValid = false;
            }

            // Email validation
            const email = document.getElementById('email');
            if (!email.value.trim()) {
                showError('email', 'Email is required');
                isValid = false;
            } else if (!/\S+@\S+\.\S+/.test(email.value)) {
                showError('email', 'Email is invalid');
                isValid = false;
            }

            // Password validation
            const password = document.getElementById('password');
            if (!password.value) {
                showError('password', 'Password is required');
                isValid = false;
            } else if (password.value.length < 6) {
                showError('password', 'Password must be at least 6 characters');
                isValid = false;
            }

            if (isValid) {
                console.log('Form submitted:', {
                    name: name.value,
                    email: email.value,
                    password: password.value
                });
                // Here you would typically send the data to your backend
            }
        });

        function showError(fieldName, message) {
            const errorElement = document.getElementById(`${fieldName}Error`);
            const inputElement = document.getElementById(fieldName);
            errorElement.textContent = message;
            errorElement.classList.remove('hidden');
            inputElement.classList.add('border-red-500');
        }
    });
</script>
</body>
</html>