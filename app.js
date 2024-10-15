function calcularHuella() {
    const consumoCarne = parseInt(document.getElementById("consumo-carne").value) || 0;
    const consumoVegetales = parseInt(document.getElementById("consumo-vegetales").value) || 0;
    const tamanoCasa = document.getElementById("tamano-casa").value;
    const energiaRenovable = document.getElementById("energia-renovable").value === "si"; 
    const transportePublico = parseInt(document.getElementById("transporte-publico").value) || 0;
    const kilometrosAuto = parseInt(document.getElementById("kilometros-auto").value) || 0;

    const data = {
        consumoCarne: consumoCarne,
        consumoVegetales: consumoVegetales,
        tamanoCasa: tamanoCasa,
        energiaRenovable: energiaRenovable,
        transportePublico: transportePublico,
        kilometrosAuto: kilometrosAuto
    };
    fetch("http://localhost:4567/calcular", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams(data)
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('resultado-huella').innerText = `Tu huella ecológica es: ${data.huellaTotal}`;
        mostrarEvaluacion(data.evaluacion);
    })
    .catch(error => console.error("Error:", error));
}

function mostrarEvaluacion(evaluacion) {
    const tabla = document.createElement('table');
    tabla.innerHTML = `
        <tr>
            <th>Rango de Huella</th>
            <th>Evaluación</th>
        </tr>
        <tr>
            <td>Menor a 10</td>
            <td>¡Estás muy bien!</td>
        </tr>
        <tr>
            <td>10 - 20</td>
            <td>Vas bien, pero hay áreas para mejorar.</td>
        </tr>
        <tr>
            <td>20 - 30</td>
            <td>Estás en un rango promedio, considera reducir tu huella.</td>
        </tr>
        <tr>
            <td> Mayor a 30</td>
            <td>¡Tu huella ecológica es alta! Es necesario hacer cambios.</td>
        </tr>
    `;
    const resultadosDiv = document.getElementById('resultados');
    resultadosDiv.appendChild(tabla);
}
