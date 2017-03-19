package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import iterators.PolicyIteration;
import iterators.ValueIteration;

import org.apache.poi.ss.usermodel.Cell;

public class GraphUtil {
	public static void main(String[] args) throws IOException, InvalidFormatException{
		ValueIteration valueIter = new ValueIteration();
		valueIter.doValueIteration();
		ArrayList<double[][]> utilsVsIter = valueIter.getUtilsVsIter();
		
		for(int i=0; i<6; i++){
			for(int j=0; j<6; j++){
				double[] utility = new double[utilsVsIter.size()];
				String filename = i + "_" + j + ".xlsx";
				for(int k=0; k < utilsVsIter.size(); k++){
					double[][] currUtils = utilsVsIter.get(k);
					utility[k] = currUtils[i][j];
				}
				generateExcelChart(utility,"plots\\value iteration\\" + filename);
			}
		}
		
		PolicyIteration policyIter = new PolicyIteration();
		policyIter.doPolicyIteration();
		utilsVsIter = policyIter.getUtilsVsIter();
		
		for(int i=0; i<6; i++){
			for(int j=0; j<6; j++){
				double[] utility = new double[utilsVsIter.size()];
				String filename = i + "_" + j + ".xlsx";
				for(int k=0; k < utilsVsIter.size(); k++){
					double[][] currUtils = utilsVsIter.get(k);
					utility[k] = currUtils[i][j];
				}
				generateExcelChart(utility,"plots\\policy iteration\\" + filename);
			}
		}
	}

	private static void generateExcelChart(double[] utilityVsIter, String filename) throws IOException, InvalidFormatException{
		File file = new File(filename);
		if(!file.exists())
			return;
		
		//Load sample Excel file
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(filename)));
		Sheet sh = workbook.getSheetAt(0);
				
		Cell iterationNum = null;
		Cell utility = null;
		
		int iterations = utilityVsIter.length-1;
		if(sh.getRow(0) == null){
			Row row = sh.createRow(0);
			Cell cell = row.getCell(0);
			if(cell == null)
				cell = row.createCell(0);
		}
		sh.getRow(0).getCell(0).setCellValue(iterations);
		for(int i = 2; i <= iterations+2; i++){
			Row r = sh.getRow(i);
			if(r == null)
				r = sh.createRow(i);
			
			if(r.getCell(0) == null)
				iterationNum = r.createCell(0);
			else
				iterationNum = r.getCell(0);
			
			if(r.getCell(1) != null)
				utility = r.getCell(1);
			else
				utility = r.createCell(1);
			
			iterationNum.setCellValue(i-2);
			utility.setCellValue(utilityVsIter[i-2]);
		}
		
		FileOutputStream f = new FileOutputStream(filename);
		workbook.write(f);
		f.close();		
		workbook.close();
	}
}
