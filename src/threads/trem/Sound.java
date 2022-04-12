package threads.trem;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	private Clip clip;
	public boolean tocando;

	public Sound(String musicLocation) {
		this.tocando = false;
		try {
			File musicPath = new File(musicLocation);

			if(musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				this.clip = AudioSystem.getClip();
				clip.open(audioInput);
			}
			else {
				System.out.println("Can't find file");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void tocarMusica() {
		this.tocando = true;
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void pararMusica() {
		this.tocando = false;
		clip.stop();
	}
}

