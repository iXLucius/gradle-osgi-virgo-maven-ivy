/**
 * 
 */
package org.lucius.framework.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class ZipUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 解压文件操作
	 * 
	 * @param file
	 *            源文件对象
	 * @param dest
	 *            解压的目标路径
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void unZip(File file1, String dest) {
		 try {
		 Charset CP866 = Charset.forName("CP866");
         ZipFile zipFile = new ZipFile(file1.getAbsolutePath(), CP866);
         Enumeration emu = zipFile.entries();
         int i=0;
         while(emu.hasMoreElements()){
             ZipEntry entry = (ZipEntry)emu.nextElement();
             //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
             if (entry.isDirectory())
             {
                 new File(dest +File.separator+ entry.getName().replace("\\",File.separator)).mkdirs();
                 continue;
             }
             BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
             File file = new File(dest +File.separator+ entry.getName().replace("\\",File.separator));
             //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
             //而这个文件所在的目录还没有出现过，所以要建出目录来。
             File parent = file.getParentFile();
             if(parent != null && (!parent.exists())){
                 boolean success = parent.mkdirs();
                 logger.info("create folder {}",success);
             }
             FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);           
             
             int count;
             byte data[] = new byte[BUFFER];
             while ((count = bis.read(data, 0, BUFFER)) != -1)
             {
                 bos.write(data, 0, count);
             }
             bos.flush();
             bos.close();
             bis.close();
             fos.close();
         }
         zipFile.close();
     } catch (Exception e) {
         e.printStackTrace();
     }
	}
	
	public static boolean zip(String srcDirName, String zipFilePath) {
		boolean flag = false;
		File srcdir = new File(srcDirName);
		if (!srcdir.exists())
			throw new RuntimeException(srcDirName + " is not exist!");

		Project prj = new Project();
		Zip zip_ = new Zip();
		zip_.setProject(prj);
		// 设置压缩包不压缩
		zip_.setCompress(false);
		zip_.setDestFile(new File(zipFilePath));
		zip_.setEncoding("UTF-8");
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		zip_.addFileset(fileSet);
		zip_.execute();
		flag = true;
		return flag;
	}
	
	
	static final int BUFFER = 2048;

 
	public static void main(String[] args)  {
		zip("G:\\013a75f39236ac4a949101bc9686ac2509\\", "G:\\013a75f39236ac4a949101bc9686ac2509.zip");
	}
}
