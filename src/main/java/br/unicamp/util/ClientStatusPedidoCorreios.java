package br.unicamp.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by enrique on 10/8/16.
 */
public class ClientStatusPedidoCorreios {

    private final String url;

    public ClientStatusPedidoCorreios(String url) {
        this.url = url;
    }

    public ConsultaStatusResponse consultarRastreio(String codigoRastreio) {

        ConsultaStatusResponse consultaStatusResponse = new ConsultaStatusResponse();

        try {
            String xmlin = "";
            URL url = new URL(this.url);

            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setAllowUserInteraction(false);

            PrintStream outStream = new PrintStream(connection.getOutputStream());
            outStream.println("usuario=ECT&senha=SRO&tipo=L&resultado=T&objetos=" + xmlin);
            outStream.close();



            DataInputStream inStream = new DataInputStream(connection.getInputStream());
            String inputLine;
            while ((inputLine = inStream.readLine()) != null) {
                if (inputLine.contains("</evento>")) {
                    break;
                }
                if (inputLine.contains("<data>")) {
                    consultaStatusResponse.setData( extraiValor(inputLine) );
                }
                if (inputLine.contains("<hora>")) {
                    consultaStatusResponse.setHora( extraiValor(inputLine) );
                }
                if (inputLine.contains("<descricao>")) {
                    consultaStatusResponse.setStatus( extraiValor(inputLine) );
                }
                if (inputLine.contains("<local>")) {
                    consultaStatusResponse.setLocal( extraiValor(inputLine) );
                }
            }
            inStream.close();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return consultaStatusResponse;
    }

    private String extraiValor(String inputLine) {
        inputLine = inputLine.trim();
        return inputLine.substring(inputLine.indexOf(">")+1, inputLine.substring(1).indexOf("<")+1) ;
    }

    public static class ConsultaStatusResponse {
        private String data;
        private String hora;
        private String status;
        private String local;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
        }
    }
}