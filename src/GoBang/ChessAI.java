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

	// public int turn1 = 1;// 判断当前轮到谁，1表示黑方，2表示白方
	int maxrc[] = new int[2];

	// public Graphics2D g;
	// public String type = "人机对战";

	// public int turn = 1;// 判断当前轮到谁，1表示黑方，2表示白方
	// public ArrayList<chess> list = new ArrayList<chess>();//
	// 声明数组列表对象，存储Chess对象

	public ChessAI(GoBang gb) {
		this.gb = gb;
	}

	static HashMap<String, Integer> map = new HashMap<String, Integer>();
	static {
		// 活连
		map.put("010", 40);
		map.put("0110", 400);
		map.put("01110", 3000);
		map.put("011110", 10000);
		map.put("020", 20);
		map.put("0220", 200);
		map.put("02220", 500);
		map.put("022220", 3000);

		// 眠连
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

	// 根据棋子数组中棋子相连的情况来计算权值存入到数组中
	public void WeightCount() {

		//System.out.println("sssssssssss" + gb.isArrive.length);

		for (int r = 0; r < gb.isArrive.length; r++) {

			for (int c = 0; c < gb.isArrive[r].length; c++) {

				// 判断此位置有无棋子
				if (gb.isArrive[r][c] == 0) {
					// 水平方向
					String code = "0";// 用来记录棋子相连情况
					int chess = 0;// 记录第一次出现的棋子
					int number = 0;// 记录空位出现的次数

					// 水平方向
					// 水平向左
					for (int c1 = c - 1; c1 >= 0; c1--) {
						if (gb.isArrive[r][c1] == 0) {
							if (c == c1 + 1) {// 在c1+1处就空了，说明是连续两个空位，就不用再讨论啦
								break;
							}
							// 不是连续两个空位的情况
							else if (number == 0) {// 表示第一次出现空位
								code = code + gb.isArrive[r][c1];// 记录棋子相连的情况
								number++;// 空位的次数加1
							} else if (number == 1) {// 表示第二次出现空位
								if (gb.isArrive[r][c1] == gb.isArrive[r][c1 + 1]) {
									break; // 检测两个位置是否都为空位
								}

								code = code + gb.isArrive[r][c1];// 记录棋子相连的情况
								number++;
							} else if (number == 2) {// 表示第三次出现空位
								if (gb.isArrive[r][c1] == gb.isArrive[r][c]) // 检测两个位置是否都为空位
									break;
							}
						} else {
							if (chess == 0) {// 表示第一次出现棋子
								chess = gb.isArrive[r][c1];// 存储第一次出现棋子
								code = code + gb.isArrive[r][c1]; // 记录棋子的相连情况
							} else if (chess == gb.isArrive[r][c1]) {// 判断是否和第一次出现的棋子同色
								code = code + gb.isArrive[r][c1]; // 记录棋子的相连情况
							} else {// 表示此位置的棋子的颜色与第一次出现的颜色不同，所以记录
								code = code + gb.isArrive[r][c1]; // 记录棋子的相连情况
								break;
							}

						}
					}

					Integer value = map.get(code);
					if (value != null)// 判断value是否不为null
						weightArray[r][c] += value;// 在对应空位累加权值

					// 水平向右
					for (int c1 = c + 1; c1 < gb.isArrive[r].length; c1++) {
						if (gb.isArrive[r][c1] == 0) {
							if (c == c1 - 1) {// 在c1+1处就空了，说明是连续两个空位，就不用再讨论啦
								break;
							}
							// 不是连续两个空位的情况
							else if (number == 0) {// 表示第一次出现空位
								code = code + gb.isArrive[r][c1];// 记录棋子相连的情况
								number++;// 空位的次数加1
							} else if (number == 1) {// 表示第二次出现空位
								if (c1 + 1 < gb.isArrive[r].length
										&& gb.isArrive[r][c1] == gb.isArrive[r][c1 + 1]) {
									break; // 检测两个位置是否都为空位
								}

								code = code + gb.isArrive[r][c1];// 记录棋子相连的情况
								number++;
							} else if (number == 2) {// 表示第三次出现空位
								if (gb.isArrive[r][c1] == gb.isArrive[r][c]) // 检测两个位置是否都为空位
									break;
							}
						} else {
							if (chess == 0) {// 表示第一次出现棋子
								chess = gb.isArrive[r][c1];// 存储第一次出现棋子
								code = code + gb.isArrive[r][c1]; // 记录棋子的相连情况
							} else if (chess == gb.isArrive[r][c1]) {// 判断是否和第一次出现的棋子同色
								code = code + gb.isArrive[r][c1]; // 记录棋子的相连情况
							} else {// 表示此位置的棋子的颜色与第一次出现的颜色不同，所以记录
								code = code + gb.isArrive[r][c1]; // 记录棋子的相连情况
								break;
							}

						}
					}
					value = map.get(code);
					if (value != null)// 判断value是否不为null
						weightArray[r][c] += value;// 在对应空位累加权值

					// 竖直方向
					// 竖直向上检查
					for (int r1 = r - 1; r1 >= 0; r1--) {
						if (gb.isArrive[r1][c] == 0) {
							if (r == r1 + 1) {// 在c1+1处就空了，说明是连续两个空位，就不用再讨论啦
								break;
							}
							// 不是连续两个空位的情况
							else if (number == 0) {// 表示第一次出现空位
								code = code + gb.isArrive[r1][c];// 记录棋子相连的情况
								number++;// 空位的次数加1
							} else if (number == 1) {// 表示第二次出现空位
								if (gb.isArrive[r1][c] == gb.isArrive[r1 + 1][c]) {
									break; // 检测两个位置是否都为空位
								}

								code = code + gb.isArrive[r1][c];// 记录棋子相连的情况
								number++;
							} else if (number == 2) {// 表示第三次出现空位
								if (gb.isArrive[r1][c] == gb.isArrive[r][c]) // 检测两个位置是否都为空位
									break;
							}
						} else {
							if (chess == 0) {// 表示第一次出现棋子
								chess = gb.isArrive[r1][c];// 存储第一次出现棋子
								code = code + gb.isArrive[r1][c]; // 记录棋子的相连情况
							} else if (chess == gb.isArrive[r1][c]) {// 判断是否和第一次出现的棋子同色
								code = code + gb.isArrive[r1][c]; // 记录棋子的相连情况
							} else {// 表示此位置的棋子的颜色与第一次出现的颜色不同，所以记录
								code = code + gb.isArrive[r1][c]; // 记录棋子的相连情况
								break;
							}

						}
					}
					value = map.get(code);
					if (value != null)// 判断value是否不为null
						weightArray[r][c] += value;// 在对应空位累加权值

					// 竖直向下检查
					for (int r1 = r + 1; r1 < gb.isArrive.length; r1++) {
						if (gb.isArrive[r1][c] == 0) {
							if (r == r1 - 1) {// 在c1+1处就空了，说明是连续两个空位，就不用再讨论啦
								break;
							}
							// 不是连续两个空位的情况
							else if (number == 0) {// 表示第一次出现空位
								code = code + gb.isArrive[r1][c];// 记录棋子相连的情况
								number++;// 空位的次数加1
							} else if (number == 1) {// 表示第二次出现空位
								if (r1 + 1 < gb.isArrive.length
										&& gb.isArrive[r1][c] == gb.isArrive[r1 + 1][c]) {
									break; // 检测两个位置是否都为空位
								}

								code = code + gb.isArrive[r1][c];// 记录棋子相连的情况
								number++;
							} else if (number == 2) {// 表示第三次出现空位
								if (gb.isArrive[r1][c] == gb.isArrive[r][c]) // 检测两个位置是否都为空位
									break;
							}
						} else {
							if (chess == 0) {// 表示第一次出现棋子
								chess = gb.isArrive[r1][c];// 存储第一次出现棋子
								code = code + gb.isArrive[r1][c]; // 记录棋子的相连情况
							} else if (chess == gb.isArrive[r1][c]) {// 判断是否和第一次出现的棋子同色
								code = code + gb.isArrive[r1][c]; // 记录棋子的相连情况
							} else {// 表示此位置的棋子的颜色与第一次出现的颜色不同，所以记录
								code = code + gb.isArrive[r1][c]; // 记录棋子的相连情况
								break;
							}

						}
					}
					value = map.get(code);
					if (value != null)// 判断value是否不为null
						weightArray[r][c] += value;// 在对应空位累加权值

					// 右斜方向
					// 向左上
					for (int r1 = r - 1, c1 = c - 1; r1 > 0 && c1 > 0; r1--, c1--) {

						if (gb.isArrive[r1][c] == 0) {
							if (r == r1 + 1 && c == c1 + 1) {// 在c1+1处就空了，说明是连续两个空位，就不用再讨论啦
								break;
							}
							// 不是连续两个空位的情况
							else if (number == 0) {// 表示第一次出现空位
								code = code + gb.isArrive[r1][c1];// 记录棋子相连的情况
								number++;// 空位的次数加1
							} else if (number == 1) {// 表示第二次出现空位
								if (r1 + 1 < gb.isArrive.length
										&& c1 + 1 < gb.isArrive[r].length
										&& gb.isArrive[r1][c1] == gb.isArrive[r1 + 1][c1 + 1]) {
									break; // 检测两个位置是否都为空位
								}

								code = code + gb.isArrive[r1][c1];// 记录棋子相连的情况
								number++;
							} else if (number == 2) {// 表示第三次出现空位
								if (gb.isArrive[r1][c1] == gb.isArrive[r][c]) // 检测两个位置是否都为空位
									break;
							}
						} else {
							if (chess == 0) {// 表示第一次出现棋子
								chess = gb.isArrive[r1][c1];// 存储第一次出现棋子
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
							} else if (chess == gb.isArrive[r1][c1]) {// 判断是否和第一次出现的棋子同色
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
							} else {// 表示此位置的棋子的颜色与第一次出现的颜色不同，所以记录
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
								break;
							}

						}
					}
					value = map.get(code);
					if (value != null)// 判断value是否不为null
						weightArray[r][c] += value;// 在对应空位累加权值

					// 向右下
					for (int r1 = r + 1, c1 = c + 1; r1 < gb.isArrive.length
							&& c1 < gb.isArrive[r].length; r1++, c1++) {

						if (gb.isArrive[r1][c1] == 0) {
							if (r == r1 - 1 && c == c1 - 1) {// 在c1+1处就空了，说明是连续两个空位，就不用再讨论啦
								break;
							}
							// 不是连续两个空位的情况
							else if (number == 0) {// 表示第一次出现空位
								code = code + gb.isArrive[r1][c1];// 记录棋子相连的情况
								number++;// 空位的次数加1
							} else if (number == 1) {// 表示第二次出现空位
								if (r1 + 1 < gb.isArrive.length
										&& c1 + 1 < gb.isArrive[r1].length
										&& (gb.isArrive[r1][c1] == gb.isArrive[r1 + 1][c1 + 1])) {
									break; // 检测两个位置是否都为空位
								}

								code = code + gb.isArrive[r1][c1];// 记录棋子相连的情况
								number++;
							} else if (number == 2) {// 表示第三次出现空位
								if (gb.isArrive[r1][c1] == gb.isArrive[r][c]) // 检测两个位置是否都为空位
									break;
							}
						} else {
							if (chess == 0) {// 表示第一次出现棋子
								chess = gb.isArrive[r1][c1];// 存储第一次出现棋子
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
							} else if (chess == gb.isArrive[r1][c1]) {// 判断是否和第一次出现的棋子同色
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
							} else {// 表示此位置的棋子的颜色与第一次出现的颜色不同，所以记录
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
								break;
							}

						}

					}
					value = map.get(code);
					if (value != null)// 判断value是否不为null
						weightArray[r][c] += value;// 在对应空位累加权值

					// 左斜方向
					// 向右上
					for (int r1 = r - 1, c1 = c + 1; r1 >= 0
							&& c1 < gb.isArrive[r].length; r1--, c1++) {
						if (gb.isArrive[r1][c1] == 0) {
							if (r == r1 + 1 && c == c1 - 1) {// 在c1+1处就空了，说明是连续两个空位，就不用再讨论啦
								break;
							}
							// 不是连续两个空位的情况
							else if (number == 0) {// 表示第一次出现空位
								code = code + gb.isArrive[r1][c1];// 记录棋子相连的情况
								number++;// 空位的次数加1
							} else if (number == 1) {// 表示第二次出现空位
								if (c1 + 1 < gb.isArrive[r].length
										&& r1 - 1 > 0
										&& gb.isArrive[r1][c1] == gb.isArrive[r1 - 1][c1 + 1]) {
									break; // 检测两个位置是否都为空位
								}

								code = code + gb.isArrive[r1][c1];// 记录棋子相连的情况
								number++;
							} else if (number == 2) {// 表示第三次出现空位
								if (gb.isArrive[r1][c1] == gb.isArrive[r][c]) // 检测两个位置是否都为空位
									break;
							}
						} else {
							if (chess == 0) {// 表示第一次出现棋子
								chess = gb.isArrive[r1][c1];// 存储第一次出现棋子
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
							} else if (chess == gb.isArrive[r1][c1]) {// 判断是否和第一次出现的棋子同色
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
							} else {// 表示此位置的棋子的颜色与第一次出现的颜色不同，所以记录
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
								break;
							}

						}

					}
					value = map.get(code);
					if (value != null)// 判断value是否不为null
						weightArray[r][c] += value;// 在对应空位累加权值

					// 向左下
					for (int r1 = r + 1, c1 = c - 1; r1 < gb.isArrive.length
							&& c1 > 0; r1++, c1--) {
						if (gb.isArrive[r1][c1] == 0) {
							if (r == r1 - 1 && c == c1 + 1) {// 在c1+1处就空了，说明是连续两个空位，就不用再讨论啦
								break;
							}
							// 不是连续两个空位的情况
							else if (number == 0) {// 表示第一次出现空位
								code = code + gb.isArrive[r1][c1];// 记录棋子相连的情况
								number++;// 空位的次数加1
							} else if (number == 1) {// 表示第二次出现空位
								if (r1 + 1 < gb.isArrive.length
										&& c1 - 1 > 0
										&& gb.isArrive[r1][c1] == gb.isArrive[r1 + 1][c1 - 1]) {
									break; // 检测两个位置是否都为空位
								}

								code = code + gb.isArrive[r1][c1];// 记录棋子相连的情况
								number++;
							} else if (number == 2) {// 表示第三次出现空位
								if (gb.isArrive[r1][c1] == gb.isArrive[r][c]) // 检测两个位置是否都为空位
									break;
							}
						} else {
							if (chess == 0) {// 表示第一次出现棋子
								chess = gb.isArrive[r1][c1];// 存储第一次出现棋子
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
							} else if (chess == gb.isArrive[r1][c1]) {// 判断是否和第一次出现的棋子同色
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
							} else {// 表示此位置的棋子的颜色与第一次出现的颜色不同，所以记录
								code = code + gb.isArrive[r1][c1]; // 记录棋子的相连情况
								break;
							}

						}

					}

					value = map.get(code);
					if (value != null)// 判断value是否不为null
						weightArray[r][c] += value;// 在对应空位累加权值

				}
			}
		}
	}

	public int[] findmax() { // 得到最大权值的行列，用数组来储存
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
	 * public void play(){//根据ROW,COLUMN来下棋
	 * 
	 * if(turn1==1) gb.isArrive[ROW][COLUMN]=1; else gb.isArrive[ROW][COLUMN]=2;
	 * gb.repaint(); }
	 */
	/*
	 * public void aiplay(GoBang go){ this.WeightCount();
	 * this.findmax(go.isArrive); this.play(); }
	 */

}
