function convertPointsToMoney() {
    const points = document.getElementById('points').value;
    fetch(`/convertPointsToMoney?points=${points}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('moneyResult').innerText = `환전된 가상 돈: ${data.money}`;
        })
        .catch(error => console.error('Error:', error));
}

function convertMoneyToPoints() {
    const money = document.getElementById('money').value;
    fetch(`/convertMoneyToPoints?money=${money}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('pointsResult').innerText = `환전된 포인트: ${data.points}`;
        })
        .catch(error => console.error('Error:', error));
}
