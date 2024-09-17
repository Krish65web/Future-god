document.getElementById('prediction-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form submission

    const name = document.getElementById('name').value;
    const dob = document.getElementById('dob').value;

    if (name && dob) {
        // Hide the form and start the countdown and animation
        document.getElementById('prediction-form').classList.add('hidden');
        document.getElementById('praise-wait-animation').style.display = 'block';
        document.getElementById('countdown').style.display = 'block';

        // Calculate the age based on the date of birth
        const birthDate = new Date(dob);
        const ageDiff = Date.now() - birthDate.getTime();
        const ageDate = new Date(ageDiff);
        const age = Math.abs(ageDate.getUTCFullYear() - 1970); // Calculate the age

        // Create a consistent "future" based on name and date of birth (using a hash function)
        function hashCode(str) {
            return str.split('').reduce((prevHash, currVal) =>
                ((prevHash << 5) - prevHash) + currVal.charCodeAt(0), 0);
        }

        // Generate a deterministic hash based on name and dob
        const seed = hashCode(name + dob);
        const randomIncome = Math.abs(seed % 500000) + 50000; // Random income between 50,000 and 500,000
        const marriageYear = new Date().getFullYear() + (Math.abs(seed % 6) + 1); // Random marriage year between current year + 1 to +6
        const randomChildren = (seed % 2) === 0 ? "a daughter" : "a son"; // Consistent child gender

        let countdownValue = 15;
        const countdownTimer = document.getElementById('countdown');
        const countdownInterval = setInterval(function () {
            countdownValue--;
            countdownTimer.textContent = countdownValue;

            if (countdownValue <= 0) {
                clearInterval(countdownInterval);

                // Stop the animation and show sharing instruction
                document.getElementById('praise-wait-animation').style.display = 'none';
                document.getElementById('countdown').style.display = 'none';
                document.getElementById('share-instruction').style.display = 'block';

                // Initialize share count
                let shareCount = 0;
                const shareBtn = document.getElementById('share-btn');
                const shareCountText = document.getElementById('share-count');

                // WhatsApp Share button logic
                shareBtn.addEventListener('click', function () {
                    const websiteUrl = 'https://yourwebsite.com'; // Replace with your actual website URL
                    const shareMessage = encodeURIComponent(`I just checked my future on this website! Check yours here: ${websiteUrl}`);
                    const whatsappUrl = `https://api.whatsapp.com/send?text=${shareMessage}`;
                    window.open(whatsappUrl, '_blank');

                    // Increment share count
                    shareCount++;
                    shareCountText.textContent = `You have shared to ${shareCount}/5 groups.`;

                    // Check if the user has shared to 5 groups
                    if (shareCount >= 5) {
                        // After sharing to 5 groups, show the result
                        document.getElementById('share-instruction').style.display = 'none';
                        
                        let prediction = `${name}, you are currently ${age} years old. In the next 5 years, you will earn around $${randomIncome}.`;

                        // If the user is 35 or younger, include marriage prediction
                        if (age <= 35) {
                            prediction += ` You will get married in the year ${marriageYear}, and your first child will be ${randomChildren}.`;
                        }

                        // Show the prediction
                        document.getElementById('prediction-text').textContent = prediction;
                        document.getElementById('result').style.display = 'block';
                    }
                });
            }
        }, 1000); // Update every second
    } else {
        alert("Please enter your name and date of birth.");
    }
});
