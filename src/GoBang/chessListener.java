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
	public int turn = 1;// �жϵ�ǰ�ֵ�˭��1��ʾ�ڷ���2��ʾ�׷�
	public Graphics2D g;
	public ArrayList<chess> list = new ArrayList<chess>();// ���������б���󣬴洢Chess����
	public String type = "���˶�ս";

	public chessListener(GoBang gf, JComboBox<String> box) {
		this.gf = gf;
		this.box = box;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("��ʼ����Ϸ")) {
			gf.addMouseListener(this);
			for (int i = 0; i < gf.isArrive.length; i++)
				for (int j = 0; j < gf.isArrive[i].length; j++)
					gf.isArrive[i][j] = 0; // ��ʼ���洢���ӵ�����ʹ��ָ�����ʼ״̬
			box.setEnabled(false); // ��������ѡ������
			gf.repaint();
		}

		else if (e.getActionCommand().equals("����")) {
			if (list.size() > 0) {
				// ��list�б��л�ȡ���һ�����ӵ�λ��
				chess lastchess = list.remove(list.size() - 1);
				gf.isArrive[lastchess.r][lastchess.c] = 0;
				if (turn == 1)
					turn++;
				else
					turn--;
				gf.repaint();
			}
		}

		else if (e.getActionCommand().equals("����")) {
			if (turn == 1)
				JOptionPane.showMessageDialog(gf, "�������䣬�����ʤ��");
			else
				JOptionPane.showMessageDialog(gf, "�������䣬�����ʤ��");
			gf.removeMouseListener(this);
			box.setEnabled(true);
		} else if (e.getSource() instanceof JComboBox) {
			//JComboBox<String> box = (JComboBox<String>) e.getSource();// ��ȡ�¼�Դ����
			type = box.getSelectedItem().toString(); // ��ȡѡ��Ķ�սģʽ
		}
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (type.equals("���˶�ս")) {
			this.PERSONPLAY(x, y);

		} else if (type.equals("�˻���ս")) {
			this.AIPLAY(x, y);
		}
	}

	public void PERSONPLAY(int x, int y) {

		// ���µķ���
		// ��������Ҫ��Ľ����
		int countx = (y - 20 + size / 2) / size;
		int county = (x - 20 + size / 2) / size;
		g = (Graphics2D) gf.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); // �����
		int arrivex, arrivey; // ������
		arrivex = 20 + county * size;
		arrivey = 20 + countx * size;

		if (gf.isArrive[countx][county] != 0) // ������
		{
			JOptionPane.showMessageDialog(gf, "�˴��������ӣ��뻻һ���ط�");
		} else {// ��ǰλ�ÿ�������
			if (turn == 1) {
				// ������ɫ
				g.setColor(Color.black);

				// ����
				g.fillOval(arrivex - size / 2, arrivey - size / 2, size, size);
				gf.isArrive[countx][county] = 1;
				turn++;
			} else {
				// ������ɫ
				g.setColor(Color.white);

				// ����
				g.fillOval(arrivex - size / 2, arrivey - size / 2, size, size);
				gf.isArrive[countx][county] = 2;
				turn--;

			}
			//

			list.add(new chess(countx, county));// ����ش洢ÿ�����ӵ�����

			// �ж���Ӯ
			if (Gobangwin.judge(gf.isArrive, countx, county)) {
				if (turn == 2)
					JOptionPane.showMessageDialog(gf, "����ʤ��");
				else
					JOptionPane.showMessageDialog(gf, "����ʤ��");
				box.setEnabled(true);
				gf.removeMouseListener(this);
			}
		}

	}

	public void AIPLAY(int x, int y) {
		// ����
		// ��������Ҫ��Ľ����
		int countx = (y - 20 + size / 2) / size;
		int county = (x - 20 + size / 2) / size;
		g = (Graphics2D) gf.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); // �����
		int arrivex, arrivey;
		// �����ϵľ���λ��
		arrivex = 20 + county * size;
		arrivey = 20 + countx * size;

		if (countx >= 0 && countx <= 15 && county >= 0 && county <= 15
				&& gf.isArrive[countx][county] != 0) // ������
		{
			JOptionPane.showMessageDialog(gf, "�˴��������ӣ��뻻һ���ط�");
		} else {
			// �����˺���
			g.setColor(Color.black);
			g.fillOval(arrivex - size / 2, arrivey - size / 2, size, size);
			gf.isArrive[countx][county] = 1;
			list.add(new chess(countx, county));// ����ش洢ÿ�����ӵ�����
			// �ж���Ӯ
			if (Gobangwin.judge(gf.isArrive, countx, county)) {
				JOptionPane.showMessageDialog(gf, "����ʤ��");
				gf.removeMouseListener(this);
				box.setEnabled(true);
				return;
			}
			ChessAI c = new ChessAI(gf);
			// System.out.println(">>>"+gf.isArrive[0].length+"<<<");
			// �ֵ�AI��
			c.WeightCount();
			int maxrc[] = c.findmax();
			arrivex = 20 + maxrc[1] * size;
			arrivey = 20 + maxrc[0] * size;
			g.setColor(Color.white);
			g.fillOval(arrivex - size / 2, arrivey - size / 2, size, size);
			gf.isArrive[maxrc[0]][maxrc[1]] = 2;
			list.add(new chess(maxrc[0], maxrc[1]));// ����ش洢ÿ�����ӵ�����

			// �ж���Ӯ
			if (Gobangwin.judge(gf.isArrive, maxrc[0], maxrc[1])) {
				JOptionPane.showMessageDialog(gf, "����ʤ��");
				gf.removeMouseListener(this);
				box.setEnabled(true);
			}
		}

	}

}
