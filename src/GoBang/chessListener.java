package GoBang;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

//import xxj.gobang03.Chess;

public class chessListener extends MouseAdapter implements GoBangconfig,
		ActionListener {
	public GoBang gf;
	public JComboBox<String> box;
	public int turn = 1;// 判断当前轮到谁，1表示黑方，2表示白方
	public Graphics2D g;
	public ArrayList<chess> list = new ArrayList<chess>();// 声明数组列表对象，存储Chess对象
	public String type = "人人对战";

	public chessListener(GoBang gf, JComboBox<String> box) {
		this.gf = gf;
		this.box = box;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("开始新游戏")) {
			gf.addMouseListener(this);
			for (int i = 0; i < gf.isArrive.length; i++)
				for (int j = 0; j < gf.isArrive[i].length; j++)
					gf.isArrive[i][j] = 0; // 初始化存储棋子的数组使其恢复到初始状态
			box.setEnabled(false); // 让下拉可选框锁定
			gf.repaint();
		}

		else if (e.getActionCommand().equals("悔棋")) {
			if (list.size() > 0) {
				// 从list列表中获取最后一颗棋子的位置
				chess lastchess = list.remove(list.size() - 1);
				gf.isArrive[lastchess.r][lastchess.c] = 0;
				if (turn == 1)
					turn++;
				else
					turn--;
				gf.repaint();
			}
		}

		else if (e.getActionCommand().equals("认输")) {
			if (turn == 1)
				JOptionPane.showMessageDialog(gf, "黑棋认输，白棋获胜！");
			else
				JOptionPane.showMessageDialog(gf, "白棋认输，黑棋获胜！");
			gf.removeMouseListener(this);
			box.setEnabled(true);
		} else if (e.getSource() instanceof JComboBox) {
			//JComboBox<String> box = (JComboBox<String>) e.getSource();// 获取事件源对象
			type = box.getSelectedItem().toString(); // 获取选择的对战模式
		}
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (type.equals("人人对战")) {
			this.PERSONPLAY(x, y);

		} else if (type.equals("人机对战")) {
			this.AIPLAY(x, y);
		}
	}

	public void PERSONPLAY(int x, int y) {

		// 人下的方法
		// 计算棋子要落的交叉点
		int countx = (y - 20 + size / 2) / size;
		int county = (x - 20 + size / 2) / size;
		g = (Graphics2D) gf.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); // 抗锯齿
		int arrivex, arrivey; // 棋盘上
		arrivex = 20 + county * size;
		arrivey = 20 + countx * size;

		if (gf.isArrive[countx][county] != 0) // 有棋子
		{
			JOptionPane.showMessageDialog(gf, "此处已有棋子，请换一个地方");
		} else {// 当前位置可以下棋
			if (turn == 1) {
				// 设置颜色
				g.setColor(Color.black);

				// 下棋
				g.fillOval(arrivex - size / 2, arrivey - size / 2, size, size);
				gf.isArrive[countx][county] = 1;
				turn++;
			} else {
				// 设置颜色
				g.setColor(Color.white);

				// 下棋
				g.fillOval(arrivex - size / 2, arrivey - size / 2, size, size);
				gf.isArrive[countx][county] = 2;
				turn--;

			}
			//

			list.add(new chess(countx, county));// 有序地存储每个棋子的行列

			// 判断输赢
			if (Gobangwin.judge(gf.isArrive, countx, county)) {
				if (turn == 2)
					JOptionPane.showMessageDialog(gf, "黑棋胜利");
				else
					JOptionPane.showMessageDialog(gf, "白棋胜利");
				box.setEnabled(true);
				gf.removeMouseListener(this);
			}
		}

	}

	public void AIPLAY(int x, int y) {
		// 人下
		// 计算棋子要落的交叉点
		int countx = (y - 20 + size / 2) / size;
		int county = (x - 20 + size / 2) / size;
		g = (Graphics2D) gf.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); // 抗锯齿
		int arrivex, arrivey;
		// 棋盘上的具体位置
		arrivex = 20 + county * size;
		arrivey = 20 + countx * size;

		if (countx >= 0 && countx <= 15 && county >= 0 && county <= 15
				&& gf.isArrive[countx][county] != 0) // 有棋子
		{
			JOptionPane.showMessageDialog(gf, "此处已有棋子，请换一个地方");
		} else {
			// 人下了黑棋
			g.setColor(Color.black);
			g.fillOval(arrivex - size / 2, arrivey - size / 2, size, size);
			gf.isArrive[countx][county] = 1;
			list.add(new chess(countx, county));// 有序地存储每个棋子的行列
			// 判断输赢
			if (Gobangwin.judge(gf.isArrive, countx, county)) {
				JOptionPane.showMessageDialog(gf, "黑棋胜利");
				gf.removeMouseListener(this);
				box.setEnabled(true);
				return;
			}
			ChessAI c = new ChessAI(gf);
			// System.out.println(">>>"+gf.isArrive[0].length+"<<<");
			// 轮到AI下
			c.WeightCount();
			int maxrc[] = c.findmax();
			arrivex = 20 + maxrc[1] * size;
			arrivey = 20 + maxrc[0] * size;
			g.setColor(Color.white);
			g.fillOval(arrivex - size / 2, arrivey - size / 2, size, size);
			gf.isArrive[maxrc[0]][maxrc[1]] = 2;
			list.add(new chess(maxrc[0], maxrc[1]));// 有序地存储每个棋子的行列

			// 判断输赢
			if (Gobangwin.judge(gf.isArrive, maxrc[0], maxrc[1])) {
				JOptionPane.showMessageDialog(gf, "白棋胜利");
				gf.removeMouseListener(this);
				box.setEnabled(true);
			}
		}

	}

}
