import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;



public class Prueba {
    private static final String OPENAI_API_KEY = "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"; // Reemplaza con tu clave API
    private static JTextArea chatTextArea;
public void setChatTextArea(JTextArea textArea) {
    chatTextArea = textArea;
}
        
        
    public static void main(String[] args) {
        // Crear la interfaz gráfica
        JFrame frame = new JFrame("ChatGPT Button Example");
        JPanel panel = new JPanel();
        JButton chatButton = new JButton("Enviar Mensaje");
        chatTextArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);

        // Agregar un ActionListener al botón
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el mensaje del usuario
                String userMessage = JOptionPane.showInputDialog("Ingrese su mensaje:");

                // Agregar el mensaje del usuario al historial
                appendToChat("Usuario: " + userMessage);

                // Realizar la solicitud a la API de OpenAI y mostrar la respuesta en el historial
                String response = performChatRequest(userMessage);
                appendToChat("Asistente: " + extractAssistantResponse(response));
            }
        });

        // Agregar componentes al panel
        panel.add(scrollPane);
        panel.add(chatButton);
        frame.getContentPane().add(panel);

        // Configurar la ventana
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void appendToChat(String message) {
        chatTextArea.append(message + "\n");
        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
    }

    private static String performChatRequest(String userMessage) {
        try {
            // URL de la API de OpenAI
            URL url = new URL("https://api.openai.com/v1/chat/completions");

            // Abrir conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud HTTP
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
            connection.setDoOutput(true);

            // Cuerpo de la solicitud
            String jsonInputString = "{\n" +
                    "    \"model\": \"gpt-3.5-turbo\",\n" +
                    "    \"messages\": [\n" +
                    "      {\n" +
                    "        \"role\": \"system\",\n" +
                    "        \"content\": \"You are a poetic assistant, skilled in explaining complex programming concepts with creative flair.\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"role\": \"user\",\n" +
                    "        \"content\": \"" + userMessage + "\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "}";

            // Enviar la solicitud
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonInputString);
            }

            // Obtener la respuesta
            int responseCode = connection.getResponseCode();

            // Leer la respuesta
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al realizar la solicitud";
        }
    }

    private static String extractAssistantResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray choicesArray = jsonResponse.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);
        JSONObject assistantResponse = firstChoice.getJSONObject("message");
        return assistantResponse.getString("content");
    }
}
