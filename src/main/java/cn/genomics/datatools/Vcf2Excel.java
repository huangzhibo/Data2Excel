package cn.genomics.bgitools.exceltrans;

import htsjdk.samtools.util.CloseableIterator;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;
import htsjdk.variant.vcf.VCFInfoHeaderLine;

import java.io.File;

public class Vcf2Excel {
	public static void main(String[] args){
		readVcf();
	}
	public static void readVcf() {
		
		VCFFileReader vcf = new VCFFileReader(new File("/Users/huangzhibo/workitems/06.demo/demo/demo.vcf"),false);
//		IntervalList interval = vcf.fromVcf(vcf);
//		interval.write(new File("/Users/huangzhibo/workitems/06.demo/demo/demo.vcf.intervalList"));

//		VCFHeader header = vcf.getFileHeader();
//		Iterable<VCFInfoHeaderLine> headerLine = header.getInfoHeaderLines();
//		for (VCFInfoHeaderLine vcfInfoHeaderLine : headerLine) {
//			System.out.println(vcfInfoHeaderLine.toString());
//		}
//		CloseableIterator<VariantContext> vcfLine = vcf.query("chr17", 41222826, 41258326);
		for (VariantContext variantContext : vcf) {
			System.out.print(variantContext.getChr()+"\t");
			System.out.print(variantContext.getEnd()+"\t");
			System.out.print(variantContext.getHetCount()+"\t");
			System.out.print(variantContext.getHomRefCount()+"\t");
			System.out.print(variantContext.getPhredScaledQual()+"\t");
//			System.out.print(variantContext.getAttribute("DP")+"\t");
//			System.out.print(variantContext.toString()+"\t");
			System.out.println(variantContext.isSNP());
		}
		
	
	
	}

}
