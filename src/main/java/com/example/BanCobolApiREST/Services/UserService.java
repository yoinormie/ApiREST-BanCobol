package com.example.BanCobolApiREST.Services;

import com.example.BanCobolApiREST.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Cambia esta ruta por la ubicación real de tu .exe
    private final String COBOL_EXE_PATH = "src/main/resources/bin";

    public int ejecutarSuma(int n1, int n2) {
        try {
            // 1. Formatear la entrada: ej. n1=10, n2=5 -> "0001000005"
            String tramaEntrada = String.format("%05d%05d", n1, n2);

            // 2. Configurar el proceso
            ProcessBuilder pb = new ProcessBuilder(COBOL_EXE_PATH + "/prueba.exe", tramaEntrada);

            // Unir errores a la salida estándar para capturar todo si algo falla
            pb.redirectErrorStream(true);

            // 3. Iniciar el proceso
            Process process = pb.start();

            // 4. Leer la salida (el DISPLAY de COBOL)
            String resultado;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                resultado = reader.lines().collect(Collectors.joining()).trim();
            }

            // 5. Esperar a que termine el proceso y validar
            int exitCode = process.waitFor();
            if (exitCode == 0 && resultado.matches("\\d+")) {
                return Integer.parseInt(resultado);
            } else {
                throw new RuntimeException("Error en COBOL o salida no válida: " + resultado);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al llamar al ejecutable COBOL", e);
        }
    }
}
