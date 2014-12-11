package Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import be.kayiranga.daoImpl.ImageDaoImpl;
import be.kayiranga.model.Image;

public class Test {

	public static void main(String[] args) {
		File file = new File("src/Tests/image2.jpg");
		if(file.exists()){
			try {
				FileInputStream fis = new FileInputStream(file);
//				Image image = new Image(fis, 1, false, false);
//				ImageDaoImpl imageDaoImpl = new ImageDaoImpl();
//				imageDaoImpl.createImage(image);
				System.out.println("Done...");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("File not found...");
		}
	}

}