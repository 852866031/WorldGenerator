import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
public class World {
    private int row;// define the row of the world matrix
    private int col;//define the col of the world matrix
    private int[] size=new int[2];//record the size of the matrix
    private int[][] matrix;//this is the main matrix
    private int max;//this is the maximum height of the world
    private int[][] lands=new int[0][2];//this store the lands in the world matrix
    public World(int row, int col, int max){
        this.row=row;
        this.col=col;
        this.size[0]=row;
        this.size[1]=col;
        this.matrix=new int[row][col];
        this.max=max;
    }//constructor
    public int[][] getMatrix() {
        return this.matrix;
    }
    public int[] getSize() {
        return this.size;
    }
    public void printMatrix() {
        for(int i=0; i<this.row; i++) {
            for(int j=0; j<this.col; j++) {
                System.out.print(this.matrix[i][j]+"   ");
            }
            System.out.println();
        }
    }//show the matrix in console
    public void reset() {
        for(int i=0; i<this.row; i++) {
            for(int j=0; j<this.col; j++) {
                this.matrix[i][j]=0;
            }
        }
    }//reset the whole world matrix to sea
    public JLabel[][] show() {
        int i,j;
        JLabel[][] LabelArray = new JLabel[this.row][this.col];
        JFrame mainFrame = new JFrame("The World");
        mainFrame.setSize(1900,1000);
        mainFrame.setLayout(new GridLayout(1, 1));
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(this.row, this.col));
        mainFrame.add(grid);
        Color c = Color.white;
        for(i=0;i<this.row;i++) {
            for (j = 0; j < this.col; j++) {
                JLabel current = new JLabel("",SwingConstants.CENTER);
                current.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                current.setOpaque(true);
                if(this.matrix[i][j]==0) c=new Color(0,191,255);
                else if(this.matrix[i][j]<=this.max/5 && this.matrix[i][j]>0) c=Color.yellow.brighter();
                else if(this.matrix[i][j]>this.max/5 && this.matrix[i][j]<=this.max*3/5) c=Color.green;
                if(this.matrix[i][j]<=this.max*3/5) for(int k=0; k*2<this.matrix[i][j]; k++) c=c.darker();
                current.setBackground(c);
                current.setText(Integer.toString(this.matrix[i][j]));
                LabelArray[i][j] = current;
                grid.add(LabelArray[i][j]);
            }
        }

        mainFrame.setVisible(true);
        return LabelArray;
    }
    public JLabel[][] show(int[] highlight) {
        int i,j;
        JLabel[][] LabelArray = new JLabel[this.row][this.col];
        JFrame mainFrame = new JFrame("The World");
        mainFrame.setSize(1800,1000);
        mainFrame.setLayout(new GridLayout(1, 1));
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(this.row, this.col));
        mainFrame.add(grid);
        Color c=Color.white;
        for(i=0;i<this.row;i++) {
            for (j = 0; j < this.col; j++) {
                JLabel current = new JLabel("",SwingConstants.CENTER);
                current.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                current.setOpaque(true);
                if(this.matrix[i][j]==0) c=new Color(0,191,255);
                else if(this.matrix[i][j]<=this.max/5 && this.matrix[i][j]>0) c=Color.yellow.brighter();
                else if(this.matrix[i][j]>this.max/5 && this.matrix[i][j]<=this.max*3/5) c=Color.green;
                if(this.matrix[i][j]<=this.max*3/5) for(int k=0; k*2<this.matrix[i][j]; k++) c=c.darker();
                current.setBackground(c);
                current.setText(Integer.toString(this.matrix[i][j]));
                LabelArray[i][j] = current;
                grid.add(LabelArray[i][j]);
            }
        }

        mainFrame.setVisible(true);
        return LabelArray;
    }
    public void update(JLabel[][] LabelArray) {
        for(int i=0;i<this.row;i++) {
            for (int j = 0; j < this.col; j++) {
                if(this.matrix[i][j]==0) {
                    LabelArray[i][j].setBackground(new Color(0,191,255));
                    LabelArray[i][j].setText("0");
                }
                else {
                    Color c;
                    if(this.matrix[i][j]<=this.max/5) c=Color.yellow.brighter();
                    else if(this.matrix[i][j]>this.max/5 && this.matrix[i][j]<=this.max*3/5) c=Color.green;
                    else c=Color.white;
                    if(this.matrix[i][j]<=this.max*3/5) for(int k=0; k*2<this.matrix[i][j]; k++) c=c.darker();
                    LabelArray[i][j].setBackground(c);
                    LabelArray[i][j].setText(Integer.toString(this.matrix[i][j]));
                }
            }
        }
    }
    private static int noise(int orig) {
        int result=1;
        Random rand=new Random();
        double a=rand.nextGaussian();
        if(a<0) {
            result=-1;
            a=-a;
        }
        int index= (int) (a/0.3);
        if(index==0) index++;
        if(a<0.2) return orig;
        if(a>=0.2 && a<0.3) index++;
        result=result*(index)+orig;
        return result;
    }
    public int[] VolcanoMethod(int time) {
        Random rand=new Random();
        int x=rand.nextInt(this.row);
        int y=rand.nextInt(this.col);
        int seed;
        while((x<(int) this.row/5) || (x>(int)4*this.row/5)) x=rand.nextInt(this.row);
        while((y<(int) this.col/5) || (y>(int)4*this.col/5)) y=rand.nextInt(this.row);
        int[] start=new int[2];
        start[0]=x;
        start[1]=y;
        for(int i=0; i<time; i++) {
            int x0=noise(x);
            int y0=noise(y);
            while(x0>this.row-1 || x0<0) x0=noise(x);
            while(y0>this.col-1 || y0<0) y0=noise(y);
            while(matrix[x0][y0]>this.max-1) {
                seed=rand.nextInt(4);
                if(seed==0 && x0+1<this.row-1) x0++;
                else if(seed==1 && x0-1>=0) x0--;
                else if(seed==2 && y0+1<this.row-1) y0++;
                else if(seed==3 && x0-1>=0); y0--;
            }

            this.matrix[x0][y0]++;
        }
        return start;

    }//apply the valcano method on world without updating the JFrame
    public int[] VolcanoMethod(int time, JLabel[][] LabelArray) {
        Random rand=new Random();
        int x=rand.nextInt(this.row);
        int y=rand.nextInt(this.col);
        int seed;
        while((x<(int) this.row/5) || (x>(int)4*this.row/5)) x=rand.nextInt(this.row);
        while((y<(int) this.col/5) || (y>(int)4*this.col/5)) y=rand.nextInt(this.row);
        int[] start=new int[2];
        start[0]=x;
        start[1]=y;
        for(int i=0; i<time; i++) {
            try{
                Thread.sleep(1);
            }catch(Exception e){ }
            int x0=noise(x);
            int y0=noise(y);
            while(x0>this.row-1 || x0<0) x0=noise(x);
            while(y0>this.col-1 || y0<0) y0=noise(y);
            while(matrix[x0][y0]>this.max-1) {
                seed=rand.nextInt(4);
                if(seed==0 && x0+1<this.row-1) x0++;
                else if(seed==1 && x0-1>=0) x0--;
                else if(seed==2 && y0+1<this.row-1) y0++;
                else if(seed==3 && x0-1>=0); y0--;
            }
            this.matrix[x0][y0]++;
            update(LabelArray);
            this.matrix[x][y]=(this.matrix[x+1][y]+this.matrix[x-1][y]+this.matrix[x][y+1]+this.matrix[x][y-1])/4+1;
            LabelArray[x][y].setBackground(Color.red);
        }
        Color c;
        if(this.matrix[x][y]<=this.max/5) c=Color.yellow.brighter();
        else if(this.matrix[x][y]>this.max/5 && this.matrix[x][y]<=this.max*3/5) c=Color.green;
        else c=Color.white;
        if(this.matrix[x][y]<=this.max*3/5) for(int k=0; k*2<this.matrix[x][y]; k++) c=c.darker();
        LabelArray[x][y].setBackground(c);
        return start;
    }//apply the valcano method on world and update the JFrame
    public int[] FootprintMethod(JLabel[][] LabelArray) {
        int[] start=new int[2];
        World w=new World(this.row/10,this.col/10, 3);
        w.VolcanoMethod(20);
        Random rand=new Random();
        int x=rand.nextInt(this.row);
        int y=rand.nextInt(this.col);
        while((x<(int) this.row/5) || (x>(int)4*this.row/5)) x=rand.nextInt(this.row);
        while((y<(int) this.col/5) || (y>(int)4*this.col/5)) y=rand.nextInt(this.row);
        start[0]=x;
        start[1]=y;
        for(int i=0;i<w.row;i++){
            for(int j = 0; j < w.col; j++) {
                if(x+i<this.row-1 && x+i>0 && y+j<this.col-1 && y+j>0) this.matrix[x+i][y+j]=this.matrix[x+i][y+j]+w.matrix[i][j];
                update(LabelArray);
            }
        }
        try{
            Thread.sleep(500);
        }catch(Exception e){ }
        return start;
    }
    public int[][] getLands() {
        int[][] l;
        for(int i=0; i<this.row; i++) {
            for(int j=0; j<this.col; j++) {
               if(this.matrix[i][j]>0) {
                   l = new int[this.lands.length+1][2];
                   for (int k = 0; k < this.lands.length; k++) l[k] = this.lands[k];
                   l[l.length-1][0] = i;
                   l[l.length-1][1] = j;
                   this.lands = l;
               }
            }
        }
        return this.lands;
    }
    public boolean islandCheck(int[] position){
        boolean check=true;
        if(position[0]-1>=0) {
            if(this.matrix[position[0]-1][position[1]]>0) {
                check=false;
            }
        }
        if(position[1]-1>=0) {
            if(this.matrix[position[0]][position[1]-1]>0) {
                check=false;
            }
        }
        if(position[0]+1<this.size[0]) {
            if(this.matrix[position[0]+1][position[1]]>0) {
                check=false;
            }
        }
        if(position[1]+1<this.size[1]) {
            if(this.matrix[position[0]][position[1]+1]>0) {
                check=false;
            }
        }
        return check;
    }
    public void clean(JLabel[][] LabelArray) {
        for(int i=0; i<LabelArray.length; i++) {
            for (int j = 0; j < LabelArray[0].length; j++) {
                LabelArray[i][j].setText("");
            }
        }
    }
    public static void main(String[] args) {

        int max=13;
        World w=new World(40, 72, max);
        JLabel[][] LabelArray=w.show();
        int[] start=w.VolcanoMethod(150, LabelArray);
        for(int i=1; i<15; i++) start=w.VolcanoMethod(150, LabelArray);

        try{
            Thread.sleep(1000);
        }catch(Exception e){ }
        for(int i=1; i<10; i++) start=w.FootprintMethod(LabelArray);


    }
}
