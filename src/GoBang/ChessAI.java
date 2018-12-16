package GoBang;

//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
import java.util.HashMap;

//import javax.swing.JComboBox;
//import javax.swing.JOptionPane;

public class ChessAI extends MouseAdapter implements GoBangconfig {
	public GoBang gb;

	// public int turn1 = 1;// �жϵ�ǰ�ֵ�˭��1��ʾ�ڷ���2��ʾ�׷�
	int maxrc[] = new int[2];

	// public Graphics2D g;
	// public String type = "�˻���ս";

	// public int turn = 1;// �жϵ�ǰ�ֵ�˭��1��ʾ�ڷ���2��ʾ�׷�
	// public ArrayList<chess> list = new ArrayList<chess>();//
	// ���������б���󣬴洢Chess����

	public ChessAI(GoBang gb) {
		this.gb = gb;
	}

	static HashMap<String, Integer> map = new HashMap<String, Integer>();
	static {
		// ����
		map.put("010", 40);
		map.put("0110", 400);
		map.put("01110", 3000);
		map.put("011110", 10000);
		map.put("020", 20);
		map.put("0220", 200);
		map.put("02220", 500);
		map.put("022220", 3000);

		// ����
		map.put("012", 40);
		map.put("0112", 400);
		map.put("01112", 3000);
		map.put("011112", 10000);
		map.put("021", 40);
		map.put("0221", 400);
		map.put("02221", 3000);
		map.put("022221", 10000);
	}
	private int[][] weightArray = new int[row][column];

	// ���������������������������������Ȩֵ���뵽������
	public void WeightCount() {

		//System.out.println("sssssssssss" + gb.isArrive.length);

		for (int r = 0; r < gb.isArrive.length; r++) {

			for (int c = 0; c < gb.isArrive[r].length; c++) {

				// �жϴ�λ����������
				if (gb.isArrive[r][c] == 0) {
					// ˮƽ����
					String code = "0";// ������¼�����������
					int chess = 0;// ��¼��һ�γ��ֵ�����
					int number = 0;// ��¼��λ���ֵĴ���

					// ˮƽ����
					// ˮƽ����
					for (int c1 = c - 1; c1 >= 0; c1--) {
						if (gb.isArrive[r][c1] == 0) {
							if (c == c1 + 1) {// ��c1+1���Ϳ��ˣ�˵��������������λ���Ͳ�����������
								break;
							}
							// ��������������λ�����
							else if (number == 0) {// ��ʾ��һ�γ��ֿ�λ
								code = code + gb.isArrive[r][c1];// ��¼�������������
								number++;// ��λ�Ĵ�����1
							} else if (number == 1) {// ��ʾ�ڶ��γ��ֿ�λ
								if (gb.isArrive[r][c1] == gb.isArrive[r][c1 + 1]) {
									break; // �������λ���Ƿ�Ϊ��λ
								}

								code = code + gb.isArrive[r][c1];// ��¼�������������
								number++;
							} else if (number == 2) {// ��ʾ�����γ��ֿ�λ
								if (gb.isArrive[r][c1] == gb.isArrive[r][c]) // �������λ���Ƿ�Ϊ��λ
									break;
							}
						} else {
							if (chess == 0) {// ��ʾ��һ�γ�������
								chess = gb.isArrive[r][c1];// �洢��һ�γ�������
								code = code + gb.isArrive[r][c1]; // ��¼���ӵ��������
							} else if (chess == gb.isArrive[r][c1]) {// �ж��Ƿ�͵�һ�γ��ֵ�����ͬɫ
								code = code + gb.isArrive[r][c1]; // ��¼���ӵ��������
							} else {// ��ʾ��λ�õ����ӵ���ɫ���һ�γ��ֵ���ɫ��ͬ�����Լ�¼
								code = code + gb.isArrive[r][c1]; // ��¼���ӵ��������
								break;
							}

						}
					}

					Integer value = map.get(code);
					if (value != null)// �ж�value�Ƿ�Ϊnull
						weightArray[r][c] += value;// �ڶ�Ӧ��λ�ۼ�Ȩֵ

					// ˮƽ����
					for (int c1 = c + 1; c1 < gb.isArrive[r].length; c1++) {
						if (gb.isArrive[r][c1] == 0) {
							if (c == c1 - 1) {// ��c1+1���Ϳ��ˣ�˵��������������λ���Ͳ�����������
								break;
							}
							// ��������������λ�����
							else if (number == 0) {// ��ʾ��һ�γ��ֿ�λ
								code = code + gb.isArrive[r][c1];// ��¼�������������
								number++;// ��λ�Ĵ�����1
							} else if (number == 1) {// ��ʾ�ڶ��γ��ֿ�λ
								if (c1 + 1 < gb.isArrive[r].length
										&& gb.isArrive[r][c1] == gb.isArrive[r][c1 + 1]) {
									break; // �������λ���Ƿ�Ϊ��λ
								}

								code = code + gb.isArrive[r][c1];// ��¼�������������
								number++;
							} else if (number == 2) {// ��ʾ�����γ��ֿ�λ
								if (gb.isArrive[r][c1] == gb.isArrive[r][c]) // �������λ���Ƿ�Ϊ��λ
									break;
							}
						} else {
							if (chess == 0) {// ��ʾ��һ�γ�������
								chess = gb.isArrive[r][c1];// �洢��һ�γ�������
								code = code + gb.isArrive[r][c1]; // ��¼���ӵ��������
							} else if (chess == gb.isArrive[r][c1]) {// �ж��Ƿ�͵�һ�γ��ֵ�����ͬɫ
								code = code + gb.isArrive[r][c1]; // ��¼���ӵ��������
							} else {// ��ʾ��λ�õ����ӵ���ɫ���һ�γ��ֵ���ɫ��ͬ�����Լ�¼
								code = code + gb.isArrive[r][c1]; // ��¼���ӵ��������
								break;
							}

						}
					}
					value = map.get(code);
					if (value != null)// �ж�value�Ƿ�Ϊnull
						weightArray[r][c] += value;// �ڶ�Ӧ��λ�ۼ�Ȩֵ

					// ��ֱ����
					// ��ֱ���ϼ��
					for (int r1 = r - 1; r1 >= 0; r1--) {
						if (gb.isArrive[r1][c] == 0) {
							if (r == r1 + 1) {// ��c1+1���Ϳ��ˣ�˵��������������λ���Ͳ�����������
								break;
							}
							// ��������������λ�����
							else if (number == 0) {// ��ʾ��һ�γ��ֿ�λ
								code = code + gb.isArrive[r1][c];// ��¼�������������
								number++;// ��λ�Ĵ�����1
							} else if (number == 1) {// ��ʾ�ڶ��γ��ֿ�λ
								if (gb.isArrive[r1][c] == gb.isArrive[r1 + 1][c]) {
									break; // �������λ���Ƿ�Ϊ��λ
								}

								code = code + gb.isArrive[r1][c];// ��¼�������������
								number++;
							} else if (number == 2) {// ��ʾ�����γ��ֿ�λ
								if (gb.isArrive[r1][c] == gb.isArrive[r][c]) // �������λ���Ƿ�Ϊ��λ
									break;
							}
						} else {
							if (chess == 0) {// ��ʾ��һ�γ�������
								chess = gb.isArrive[r1][c];// �洢��һ�γ�������
								code = code + gb.isArrive[r1][c]; // ��¼���ӵ��������
							} else if (chess == gb.isArrive[r1][c]) {// �ж��Ƿ�͵�һ�γ��ֵ�����ͬɫ
								code = code + gb.isArrive[r1][c]; // ��¼���ӵ��������
							} else {// ��ʾ��λ�õ����ӵ���ɫ���һ�γ��ֵ���ɫ��ͬ�����Լ�¼
								code = code + gb.isArrive[r1][c]; // ��¼���ӵ��������
								break;
							}

						}
					}
					value = map.get(code);
					if (value != null)// �ж�value�Ƿ�Ϊnull
						weightArray[r][c] += value;// �ڶ�Ӧ��λ�ۼ�Ȩֵ

					// ��ֱ���¼��
					for (int r1 = r + 1; r1 < gb.isArrive.length; r1++) {
						if (gb.isArrive[r1][c] == 0) {
							if (r == r1 - 1) {// ��c1+1���Ϳ��ˣ�˵��������������λ���Ͳ�����������
								break;
							}
							// ��������������λ�����
							else if (number == 0) {// ��ʾ��һ�γ��ֿ�λ
								code = code + gb.isArrive[r1][c];// ��¼�������������
								number++;// ��λ�Ĵ�����1
							} else if (number == 1) {// ��ʾ�ڶ��γ��ֿ�λ
								if (r1 + 1 < gb.isArrive.length
										&& gb.isArrive[r1][c] == gb.isArrive[r1 + 1][c]) {
									break; // �������λ���Ƿ�Ϊ��λ
								}

								code = code + gb.isArrive[r1][c];// ��¼�������������
								number++;
							} else if (number == 2) {// ��ʾ�����γ��ֿ�λ
								if (gb.isArrive[r1][c] == gb.isArrive[r][c]) // �������λ���Ƿ�Ϊ��λ
									break;
							}
						} else {
							if (chess == 0) {// ��ʾ��һ�γ�������
								chess = gb.isArrive[r1][c];// �洢��һ�γ�������
								code = code + gb.isArrive[r1][c]; // ��¼���ӵ��������
							} else if (chess == gb.isArrive[r1][c]) {// �ж��Ƿ�͵�һ�γ��ֵ�����ͬɫ
								code = code + gb.isArrive[r1][c]; // ��¼���ӵ��������
							} else {// ��ʾ��λ�õ����ӵ���ɫ���һ�γ��ֵ���ɫ��ͬ�����Լ�¼
								code = code + gb.isArrive[r1][c]; // ��¼���ӵ��������
								break;
							}

						}
					}
					value = map.get(code);
					if (value != null)// �ж�value�Ƿ�Ϊnull
						weightArray[r][c] += value;// �ڶ�Ӧ��λ�ۼ�Ȩֵ

					// ��б����
					// ������
					for (int r1 = r - 1, c1 = c - 1; r1 > 0 && c1 > 0; r1--, c1--) {

						if (gb.isArrive[r1][c] == 0) {
							if (r == r1 + 1 && c == c1 + 1) {// ��c1+1���Ϳ��ˣ�˵��������������λ���Ͳ�����������
								break;
							}
							// ��������������λ�����
							else if (number == 0) {// ��ʾ��һ�γ��ֿ�λ
								code = code + gb.isArrive[r1][c1];// ��¼�������������
								number++;// ��λ�Ĵ�����1
							} else if (number == 1) {// ��ʾ�ڶ��γ��ֿ�λ
								if (r1 + 1 < gb.isArrive.length
										&& c1 + 1 < gb.isArrive[r].length
										&& gb.isArrive[r1][c1] == gb.isArrive[r1 + 1][c1 + 1]) {
									break; // �������λ���Ƿ�Ϊ��λ
								}

								code = code + gb.isArrive[r1][c1];// ��¼�������������
								number++;
							} else if (number == 2) {// ��ʾ�����γ��ֿ�λ
								if (gb.isArrive[r1][c1] == gb.isArrive[r][c]) // �������λ���Ƿ�Ϊ��λ
									break;
							}
						} else {
							if (chess == 0) {// ��ʾ��һ�γ�������
								chess = gb.isArrive[r1][c1];// �洢��һ�γ�������
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
							} else if (chess == gb.isArrive[r1][c1]) {// �ж��Ƿ�͵�һ�γ��ֵ�����ͬɫ
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
							} else {// ��ʾ��λ�õ����ӵ���ɫ���һ�γ��ֵ���ɫ��ͬ�����Լ�¼
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
								break;
							}

						}
					}
					value = map.get(code);
					if (value != null)// �ж�value�Ƿ�Ϊnull
						weightArray[r][c] += value;// �ڶ�Ӧ��λ�ۼ�Ȩֵ

					// ������
					for (int r1 = r + 1, c1 = c + 1; r1 < gb.isArrive.length
							&& c1 < gb.isArrive[r].length; r1++, c1++) {

						if (gb.isArrive[r1][c1] == 0) {
							if (r == r1 - 1 && c == c1 - 1) {// ��c1+1���Ϳ��ˣ�˵��������������λ���Ͳ�����������
								break;
							}
							// ��������������λ�����
							else if (number == 0) {// ��ʾ��һ�γ��ֿ�λ
								code = code + gb.isArrive[r1][c1];// ��¼�������������
								number++;// ��λ�Ĵ�����1
							} else if (number == 1) {// ��ʾ�ڶ��γ��ֿ�λ
								if (r1 + 1 < gb.isArrive.length
										&& c1 + 1 < gb.isArrive[r1].length
										&& (gb.isArrive[r1][c1] == gb.isArrive[r1 + 1][c1 + 1])) {
									break; // �������λ���Ƿ�Ϊ��λ
								}

								code = code + gb.isArrive[r1][c1];// ��¼�������������
								number++;
							} else if (number == 2) {// ��ʾ�����γ��ֿ�λ
								if (gb.isArrive[r1][c1] == gb.isArrive[r][c]) // �������λ���Ƿ�Ϊ��λ
									break;
							}
						} else {
							if (chess == 0) {// ��ʾ��һ�γ�������
								chess = gb.isArrive[r1][c1];// �洢��һ�γ�������
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
							} else if (chess == gb.isArrive[r1][c1]) {// �ж��Ƿ�͵�һ�γ��ֵ�����ͬɫ
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
							} else {// ��ʾ��λ�õ����ӵ���ɫ���һ�γ��ֵ���ɫ��ͬ�����Լ�¼
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
								break;
							}

						}

					}
					value = map.get(code);
					if (value != null)// �ж�value�Ƿ�Ϊnull
						weightArray[r][c] += value;// �ڶ�Ӧ��λ�ۼ�Ȩֵ

					// ��б����
					// ������
					for (int r1 = r - 1, c1 = c + 1; r1 >= 0
							&& c1 < gb.isArrive[r].length; r1--, c1++) {
						if (gb.isArrive[r1][c1] == 0) {
							if (r == r1 + 1 && c == c1 - 1) {// ��c1+1���Ϳ��ˣ�˵��������������λ���Ͳ�����������
								break;
							}
							// ��������������λ�����
							else if (number == 0) {// ��ʾ��һ�γ��ֿ�λ
								code = code + gb.isArrive[r1][c1];// ��¼�������������
								number++;// ��λ�Ĵ�����1
							} else if (number == 1) {// ��ʾ�ڶ��γ��ֿ�λ
								if (c1 + 1 < gb.isArrive[r].length
										&& r1 - 1 > 0
										&& gb.isArrive[r1][c1] == gb.isArrive[r1 - 1][c1 + 1]) {
									break; // �������λ���Ƿ�Ϊ��λ
								}

								code = code + gb.isArrive[r1][c1];// ��¼�������������
								number++;
							} else if (number == 2) {// ��ʾ�����γ��ֿ�λ
								if (gb.isArrive[r1][c1] == gb.isArrive[r][c]) // �������λ���Ƿ�Ϊ��λ
									break;
							}
						} else {
							if (chess == 0) {// ��ʾ��һ�γ�������
								chess = gb.isArrive[r1][c1];// �洢��һ�γ�������
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
							} else if (chess == gb.isArrive[r1][c1]) {// �ж��Ƿ�͵�һ�γ��ֵ�����ͬɫ
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
							} else {// ��ʾ��λ�õ����ӵ���ɫ���һ�γ��ֵ���ɫ��ͬ�����Լ�¼
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
								break;
							}

						}

					}
					value = map.get(code);
					if (value != null)// �ж�value�Ƿ�Ϊnull
						weightArray[r][c] += value;// �ڶ�Ӧ��λ�ۼ�Ȩֵ

					// ������
					for (int r1 = r + 1, c1 = c - 1; r1 < gb.isArrive.length
							&& c1 > 0; r1++, c1--) {
						if (gb.isArrive[r1][c1] == 0) {
							if (r == r1 - 1 && c == c1 + 1) {// ��c1+1���Ϳ��ˣ�˵��������������λ���Ͳ�����������
								break;
							}
							// ��������������λ�����
							else if (number == 0) {// ��ʾ��һ�γ��ֿ�λ
								code = code + gb.isArrive[r1][c1];// ��¼�������������
								number++;// ��λ�Ĵ�����1
							} else if (number == 1) {// ��ʾ�ڶ��γ��ֿ�λ
								if (r1 + 1 < gb.isArrive.length
										&& c1 - 1 > 0
										&& gb.isArrive[r1][c1] == gb.isArrive[r1 + 1][c1 - 1]) {
									break; // �������λ���Ƿ�Ϊ��λ
								}

								code = code + gb.isArrive[r1][c1];// ��¼�������������
								number++;
							} else if (number == 2) {// ��ʾ�����γ��ֿ�λ
								if (gb.isArrive[r1][c1] == gb.isArrive[r][c]) // �������λ���Ƿ�Ϊ��λ
									break;
							}
						} else {
							if (chess == 0) {// ��ʾ��һ�γ�������
								chess = gb.isArrive[r1][c1];// �洢��һ�γ�������
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
							} else if (chess == gb.isArrive[r1][c1]) {// �ж��Ƿ�͵�һ�γ��ֵ�����ͬɫ
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
							} else {// ��ʾ��λ�õ����ӵ���ɫ���һ�γ��ֵ���ɫ��ͬ�����Լ�¼
								code = code + gb.isArrive[r1][c1]; // ��¼���ӵ��������
								break;
							}

						}

					}

					value = map.get(code);
					if (value != null)// �ж�value�Ƿ�Ϊnull
						weightArray[r][c] += value;// �ڶ�Ӧ��λ�ۼ�Ȩֵ

				}
			}
		}
	}

	public int[] findmax() { // �õ����Ȩֵ�����У�������������
		int max = 0, i = 0, j = 0;
		for (i = 0; i < weightArray.length; i++) {
			for (j = 0; j < weightArray[i].length; j++) {
				//System.out.print(weightArray[i][j] + "\t");
				if (weightArray[i][j] > max) {
					max = weightArray[i][j];
					maxrc[0] = i;
					maxrc[1] = j;
				}
			}
			//System.out.println();
		}
		//System.out.println(maxrc[0] + "<>" + maxrc[1]);
		return maxrc;
	}

	/*
	 * public void play(){//����ROW,COLUMN������
	 * 
	 * if(turn1==1) gb.isArrive[ROW][COLUMN]=1; else gb.isArrive[ROW][COLUMN]=2;
	 * gb.repaint(); }
	 */
	/*
	 * public void aiplay(GoBang go){ this.WeightCount();
	 * this.findmax(go.isArrive); this.play(); }
	 */

}
