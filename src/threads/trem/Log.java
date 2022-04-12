package threads.trem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Log {
	
	private static boolean ativado;
	public static final String codigoErro= "a0000xa0000DELTA";
	
	public static void setAtivado(boolean ativar) {
		ativado = ativar;
		
	}
	
	public static String cabecalho() {
		LocalDateTime periodo = LocalDateTime.now();
		DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("'['dd/MM/uuuu']' '['HH:mm:ss a']': ");
		
		return periodo.format(formatacao);
		
	}
	
	public static void printlog(String id, String mensagem) {
		if (ativado) {
			if (id == Log.codigoErro) {
				System.out.println(cabecalho() + mensagem);
			} else {
				String corpo = String.format(mensagem, id);
				System.out.println(cabecalho() + corpo);
			}
			
		}
		
	}

}
