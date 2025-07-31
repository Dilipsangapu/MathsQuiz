document.addEventListener("DOMContentLoaded", async () => {
    const container = document.getElementById("questionContainer");
    const form = document.getElementById("quizForm");
    const resultDiv = document.getElementById("result");
    const scoreSpan = document.getElementById("scoreValue");
    const leaderboard = document.getElementById("leaderboard");

    const response = await fetch("/quiz/start");
    const questions = await response.json();
    let currentIndex = 0;
    const selectedAnswers = new Array(questions.length).fill("");

    const renderQuestion = () => {
        container.innerHTML = "";

        const q = questions[currentIndex];
        const block = document.createElement("div");
        block.classList.add("question-block");

        block.innerHTML = `
            <p><strong>Q${currentIndex + 1}:</strong> ${q.question}</p>
            <div class="options">
                ${q.options.map(opt => `
                    <label>
                        <input type="radio" name="answer" value="${opt}" ${selectedAnswers[currentIndex] === opt ? 'checked' : ''} required>
                        ${opt}
                    </label>
                `).join("")}
            </div>
            <div class="btn-group">
                ${currentIndex > 0 ? `<button type="button" id="prevBtn">Previous</button>` : ""}
                <button type="submit" class="submit-btn">${currentIndex === questions.length - 1 ? "Finish" : "Next"}</button>
            </div>
        `;
        container.appendChild(block);

        const prevBtn = document.getElementById("prevBtn");
        if (prevBtn) {
            prevBtn.addEventListener("click", () => {
                saveAnswer();
                currentIndex--;
                renderQuestion();
            });
        }
    };

    const saveAnswer = () => {
        const selected = document.querySelector("input[name=answer]:checked");
        if (selected) {
            selectedAnswers[currentIndex] = selected.value;
        }
    };

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        saveAnswer();

        if (selectedAnswers[currentIndex] === "") {
            alert("Please select an answer.");
            return;
        }

        if (currentIndex < questions.length - 1) {
            currentIndex++;
            renderQuestion();
        } else {
            // Submit to server
            const res = await fetch("/quiz/submit", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ selectedAnswers }) // username handled on server
            });

            const score = await res.json();
            scoreSpan.textContent = score;
            resultDiv.classList.remove("hidden");
            form.style.display = "none";

            const lbRes = await fetch("/quiz/scoreboard");
            const scores = await lbRes.json();

            leaderboard.innerHTML = "";
            scores.forEach(u => {
                const li = document.createElement("li");
                li.textContent = `${u.username}: ${u.score}`;
                leaderboard.appendChild(li);
            });
        }
    });

    renderQuestion(); // Initial load
});
