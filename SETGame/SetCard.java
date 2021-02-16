import java.awt.*;


public class SetCard {
	private int color;
	private int shape;
	private int fill;
	private int number;
	
	public static final int width = 20;
	public static final int height = 50;
	
	
	public SetCard(int aColor, int aShape, int aFill, int aNumber){
		if(aColor >=1 && aColor <= 3)
			color = aColor;
		else
			color = 1;
		
		if(aShape >=1 && aShape <= 3)
			shape = aShape;
		else
			shape = 1;
		
		if(aFill >=1 && aFill <= 3)
			fill = aFill;
		else
			fill = 1;
		
		if(aNumber >=1 && aNumber <= 3)
			number = aNumber;
		else
			number = 1;
		
	}
	
	public int getColor(){
		return color;
	}
	
	public int getShape(){
		return shape;
	}
	
	public int getFill(){
		return fill;
	}
	
	public int getNumber(){
		return number;
	}
	
	

	public void draw(Graphics g, int x, int y){
		g.setColor(Color.WHITE);
		g.fillRect(x - 90, y - 65, 180, 130);
		if(color == 1){
			g.setColor(Color.RED);
		}else if(color == 2){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.BLUE);
		}
		if(number == 1 || number == 3){
			if(shape == 1){	
				drawDiamond(g, x, y);
			}else if(shape == 2){
				drawRect(g, x, y);
			}else{
				drawSquigly(g, x, y);
			}
		}else{
			if(shape == 1){	
				drawDiamond(g, x-(int)(width * 1.5), y);
				drawDiamond(g, x+(int)(width * 1.5), y);
			}else if(shape == 2){
				drawRect(g, x-(int)(width * 1.5), y);
				drawRect(g, x+(int)(width * 1.5), y);
			}else{
				drawSquigly(g, x-(int)(width * 1.5), y);
				drawSquigly(g, x+(int)(width * 1.5), y);
			}
		}
		if(number == 3){
			if(shape == 1){	
				drawDiamond(g, x-(int)(width * 3), y);
				drawDiamond(g, x+(int)(width * 3), y);
			}else if(shape == 2){
				drawRect(g, x-(int)(width * 3), y);
				drawRect(g, x+(int)(width * 3), y);
			}else{
				drawSquigly(g, x-(int)(width * 3), y);
				drawSquigly(g, x+(int)(width * 3), y);
			}
		}
	}

	
	
	public void drawDiamond(Graphics g, int x, int y){

		int[] Xs = new int[4];
		int[] Ys = new int[4];
		
		Xs[0] = x - width;
		Ys[0] = y;

		Xs[1] = x;
		Ys[1] = y - height;

		Xs[2] = x + width;
		Ys[2] = y;

		Xs[3] = x;
		Ys[3] = y + height;

		if(fill == 3){
			g.fillPolygon(Xs, Ys, 4);
		}else{
			g.drawPolygon(Xs, Ys, 4);
			if(fill == 2){
				int yOffset;
				int xOffset;
				yOffset = height;
				while(yOffset >= 0){
					xOffset = width*(height-yOffset)/height;
					g.drawLine( x - xOffset, y-yOffset, x + xOffset, y - yOffset);
					yOffset-=2;
				}
				yOffset = height;
				while(yOffset >= 0){
					xOffset = width*(height-yOffset)/height;
					g.drawLine(x - xOffset, y+yOffset, x + xOffset, y + yOffset);
					yOffset-=2;
				}
			}
		}
	}
	
	public void drawRect(Graphics g, int x, int y){
		if(fill == 3){
			g.fillRect(x-width, y-height+width, 2*width, 2*(height-width));
			g.fillArc(x-width, y-height, 2*width, 2*width, 0, 180);
			g.fillArc(x-width, y+height-2*width, 2*width, 2*width, 180, 359);
		}else{
			g.drawLine(x-width, y-height+width, x-width, y+height-width);
			g.drawLine(x+width, y-height+width, x+width, y+height-width);
			
			g.drawArc(x-width, y-height, 2*width, 2*width, 0, 180);
			g.drawArc(x-width, y+height-2*width, 2*width, 2*width, 180, 180);
			if(fill == 2){
				g.drawLine(x-width, y-height+width, x+width, y-height+ width);
				g.drawLine(x-width, y+height-width, x+width, y+height-width);
				int yOffset, yEnd, change;
				yOffset = -height + width;
				yEnd = height - width;
				while(yOffset <= yEnd){
					g.drawLine(x - width, y + yOffset, x + width, y + yOffset);
					yOffset+=2;
				}
				
				int xOffset;
				yOffset = height;
				change = width;
				while(yOffset >= height-width){
					xOffset = (int)(Math.sqrt((double)(width*width) - (double)(change*change)));
					g.drawLine( x - xOffset, y-yOffset, x + xOffset, y - yOffset);
					yOffset -= 2;
					change -=2;
				}
				yOffset = height;
				change = width;
				while(yOffset >= height-width){
					xOffset = (int)(Math.sqrt((double)(width*width) - (double)(change*change)));
					g.drawLine(x - xOffset, y+yOffset, x + xOffset, y + yOffset);
					yOffset-=2;
					change -=2;
				}
				
			}
		}
	}

	public void drawSquigly(Graphics g, int x, int y){
		int []x1 = new int[(2 * height)];
		int []x2 = new int[2 * height];
		int []y1 = new int[(2 * height)];
		
		for(int i = 0; i < height * 2; i++){
			y1[i] = y - height + i;
			double percent = ((double)i)/((double)height);
			//System.out.println(percent);
			int xOffset = (int)(width * Math.sin(3.14*percent)/2);
			//System.out.println(xOffset);
			x1[i] = x - xOffset - width/2;
			x2[i] = x - xOffset + width/2;
			
		}
	
		g.drawPolyline(x1, y1, 2* height);
		g.drawPolyline(x2, y1, 2* height);
		
		if(fill == 3){
			for(int i = 0; i < height * 2; i++){
				g.drawLine(x1[i], y1[i], x2[i], y1[i]);	
			}
			
		}else{
			g.drawLine(x1[0], y1[0], x2[0], y1[0]);
			g.drawLine(x1[2 * height - 1], y1[2 * height - 1], x2[2 * height - 1], y1[2 * height - 1]);	
			if(fill == 2){
				for(int i = 0; i < height * 2; i+=2){
					g.drawLine(x1[i], y1[i], x2[i], y1[i]);	
				}
			}
		}
	}

}
