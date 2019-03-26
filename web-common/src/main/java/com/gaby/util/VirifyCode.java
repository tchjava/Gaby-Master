package com.gaby.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
/**
*@discrption:验证码
*@user:Gaby
*@createTime:2019-03-17 22:24
*/
public class VirifyCode {
	
	
	private String text;
	private final static int WIDTH=60;
	private final static int HEIGHT=20;
	public  BufferedImage getImage(){
		BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g=image.getGraphics();
		//产生随机验证码
		//定义验证码的字符表
		String chars="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] rands=new char[4];
		for(int i=0;i<4;i++){
			int rand=(int)(Math.random()*36);
			rands[i]=chars.charAt(rand);
		}
		//将文本给了text,还不区分大小写
		this.text=new String(rands).toLowerCase();
		//产生图像
		//画背景
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//随机产生120个干扰点
		for(int i=0;i<120;i++){
			int x=(int)(Math.random()*WIDTH);
			int y=(int)(Math.random()*HEIGHT);
			int red=(int)(Math.random()*255);
			int green=(int)(Math.random()*255);
			int blue=(int)(Math.random()*255);
			g.setColor(new Color(red,green,blue));
			g.drawOval(x, y, 1, 0);
			
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font(null,Font.ITALIC|Font.BOLD,18));
		//在不同的高度上输出验证码的不同字符
		g.drawString(""+rands[0], 1, 17);
		g.drawString(""+rands[1], 16, 15);
		g.drawString(""+rands[2], 31,18);
		g.drawString(""+rands[3], 46, 16);
		g.dispose();
		return image;
	}
	public String getText() {
		return text;
	}
	public static void wirte(RenderedImage im,String formatName,OutputStream output){
		try {
			ImageIO.write(im, formatName, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
