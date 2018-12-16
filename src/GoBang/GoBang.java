package GoBang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GoBang extends JPanel implements GoBangconfig {

	public int[][] isArrive = new int[15][15];

	public static void main(String[] args) {
		GoBang gf = new GoBang();
		gf.initUI();

	}

	public void initUI() {
		JFrame jf = new JFrame();
		jf.setTitle("五子棋游戏");// 设置标题
		jf.setSize(700, 600);// 设置大小
		jf.setLocationRelativeTo(null);// 显示位置
		jf.setDefaultCloseOperation(3);// 关闭操作

		jf.getContentPane().setBackground(Color.gray);

		jf.setLayout(new BorderLayout());

		Dimension dim1 = new Dimension(150, 0);// 设置右半部分的大小
		Dimension dim2 = new Dimension(140, 40);// 设置右边按钮的大小

		// 设置右半部分的颜色及布局
		JPanel jp1 = new JPanel();
		jp1.setPreferredSize(dim1);
		jp1.setBackground(Color.DARK_GRAY);
		jf.add(jp1, BorderLayout.EAST);

		FlowLayout F1 = new FlowLayout();
		jp1.setLayout(F1);// 设置流式布局

		// 实现左半部分的功能
		this.setBackground(Color.gray);
		jf.add(this, BorderLayout.CENTER);// 将棋盘添加到框架布局的中间位置

		// 设置选项按钮
		String[] choose = { "人人对战", "人机对战" };
		JComboBox<String> box = new JComboBox<String>(choose);
        
		
		// 设置三个普通按钮
		String[] typeArray = { "开始新游戏", "悔棋", "认输" };
		chessListener butjtq = new chessListener(this,box);

		for (int i = 0; i < typeArray.length; i++) {
			JButton but = new JButton(typeArray[i]);
			but.setPreferredSize(dim2);
			jp1.add(but);
			but.addActionListener(butjtq);
		}
		jp1.add(box);
		box.addActionListener(butjtq);

		jf.setVisible(true);
	}

	public void paint(Graphics gr) {
		super.paint(gr);

		Graphics2D g = (Graphics2D) gr;
		// 重绘棋盘
		g.setColor(Color.black);
		for (int i = 0; i < row; i++) {
			g.drawLine(x, y + size * i, x + size * (column - 1), y + size * i);
			g.drawLine(x + size * i, y, x + size * i, y + size * (row - 1));
		}

		// 重绘棋子
		for (int i = 0; i < row; i++)
			for (int j = 0; j < column; j++) {
				if (isArrive[i][j] != 0) {
					int countx = size * j + x - size / 2;
					int county = size * i + y - size / 2;
					if (isArrive[i][j] == 1)
						g.setColor(Color.black);
					else if (isArrive[i][j] == 2)
						g.setColor(Color.white);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);

					g.fillOval(countx, county, size, size);
				}
			}

	}

}
