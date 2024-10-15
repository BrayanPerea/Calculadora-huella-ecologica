import static spark.Spark.*;

public class EcologicalCalculator {
    public static void main(String[] args) {
        port(4567);
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type");
        });
        post("/calcular", (request, response) -> {
            // Obtener datos de la solicitud
            int consumoCarne = Integer.parseInt(request.queryParams("consumoCarne"));
            int consumoVegetales = Integer.parseInt(request.queryParams("consumoVegetales"));
            String tamanoCasa = request.queryParams("tamanoCasa");
            boolean energiaRenovable = Boolean.parseBoolean(request.queryParams("energiaRenovable"));
            int transportePublico = Integer.parseInt(request.queryParams("transportePublico"));
            int kilometrosAuto = Integer.parseInt(request.queryParams("kilometrosAuto"));

            // Lógica para calcular la huella ecológica
            double huellaTotal = calcularHuellaEcologica(consumoCarne, consumoVegetales, tamanoCasa, energiaRenovable, transportePublico, kilometrosAuto);
            // Evaluar el rango del puntaje
            String evaluacion = evaluarRango(huellaTotal);
            // Devolver el resultado en formato JSON
            response.type("application/json");
            return "{\"huellaTotal\": " + huellaTotal + "}";
        });
        get("/test", (req, res) -> "Servidor en funcionamiento");

    }
    private static String evaluarRango(double huella) {
        if (huella < 10) {
            return "¡Estás muy bien!";
        } else if (huella >= 10 && huella < 20) {
            return "Vas bien, pero hay áreas para mejorar.";
        } else if (huella >= 20 && huella < 30) {
            return "Estás en un rango promedio, considera reducir tu huella.";
        } else {
            return "¡Tu huella ecológica es alta! Es necesario hacer cambios.";
        }
    }
    private static double calcularHuellaEcologica(int consumoCarne, int consumoVegetales, String tamanoCasa, boolean energiaRenovable, int transportePublico, int kilometrosAuto) {
        double huellaTotal = 0;

        // Calcular huella por consumo de carne y vegetales
        huellaTotal += consumoCarne * 2;
        huellaTotal -= consumoVegetales * 0.5;

        // Calcular huella por tamaño de la casa
        switch (tamanoCasa) {
            case "pequena":
                huellaTotal += 5;
                break;
            case "mediana":
                huellaTotal += 10;
                break;
            case "grande":
                huellaTotal += 15;
                break;
        }

        // Ajustar huella por uso de energía renovable
        if (energiaRenovable) {
            huellaTotal -= 5;
        }

        // Calcular huella por uso de transporte público
        huellaTotal -= transportePublico * 0.2; // Supongamos que cada uso reduce 0.2 de la huella

        // Calcular huella por kilómetros en auto
        huellaTotal += kilometrosAuto * 0.05; // Supongamos que cada kilómetro suma 0.05 a la huella

        return huellaTotal;
    }
}