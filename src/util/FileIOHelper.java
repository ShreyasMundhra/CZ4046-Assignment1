package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

public class FileIOHelper
{
	public static void writeToFile(List<double[][]> utilsVsIter, String fileName) {
		
		StringBuilder sb = new StringBuilder();
		String pattern = "00.000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				
				Iterator<double[][]> iter = utilsVsIter.iterator();
				while(iter.hasNext()) {
					
					double[][] currUtils = iter.next();
					sb.append(decimalFormat.format(
							currUtils[row][col]).substring(0, 6));
					
					if(iter.hasNext()) {
						sb.append(",");
					}
				}
				sb.append("\n");
			}
		}
		
		writeToFile(sb.toString().trim(), "data\\" + fileName + ".csv");
	}
	
	public static void writeToFile(String content, String fileName)
	{
		try
		{
			FileWriter fw = new FileWriter(new File(fileName), false);

			fw.write(content);
			fw.close();

			System.out.println("\nSuccessfully saved results to \"" + fileName + "\".");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}