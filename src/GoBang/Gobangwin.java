package GoBang;

public class Gobangwin {

	public static boolean judge(int[][]isArrive, int r, int c){
		if(countx(isArrive,r,c)>=5||county(isArrive,r,c)>=5||countxy1(isArrive,r,c)>=5||countxy2(isArrive,r,c)>=5)
			return true;
		
		else return false;
	}
	
	
	//������ֱ�����Ƿ���������
	private static int countx(int[][] isArrive, int r, int c) {
		int count = 1;
		for (int r1 = r - 1; r1 >= 0; r1--)
			if (isArrive[r][c] == isArrive[r1][c])
				count++;
			else
				break;

		for (int r1 = r + 1; r1 < isArrive.length; r1++)
			if (isArrive[r][c] == isArrive[r1][c])
				count++;
			else
				break;
		return count;
	}
	
	
	
	//����ˮƽ�����Ƿ���������
		private static int county(int[][] isArrive, int r, int c) {
			int count = 1;
			for (int c1 = c - 1; c1 >= 0; c1--)
				if (isArrive[r][c] == isArrive[r][c1])
					count++;
				else
					break;

			for (int c1 = c + 1; c1 < isArrive[r].length; c1++)
				if (isArrive[r][c] == isArrive[r][c1])
					count++;
				else
					break;
			return count;
		}
		
		
		
		//��������������б�ķ����Ƿ���������
		private static int countxy1(int[][] isArrive, int r, int c) {
			int count = 1;  //�����Ͻ���
			for (int r1 = r - 1,c1=c-1; r1 >= 0&&c1>=0; r1--,c1--)
				if (isArrive[r][c] == isArrive[r1][c1])
					count++;
				else
					break;
           //�����½���
			for (int r1 = r + 1,c1=c+1; r1 < isArrive.length&&c1<isArrive[r].length; r1++,c1++)
				if (isArrive[r][c] == isArrive[r1][c1])
					count++;
				else
					break;
			return count;
		}
		
		
		
		//��������������б�ķ����Ƿ���������
		private static int countxy2(int[][] isArrive, int r, int c) {
			int count = 1;  //�����Ͻ���
			for (int r1 = r - 1,c1=c+1; r1 >= 0&&c1<isArrive[r].length; r1--,c1++)
				if (isArrive[r][c] == isArrive[r1][c1])
					count++;
				else
					break;
			  //�����½���
			for (int r1 = r + 1,c1=c-1; r1 < isArrive.length&&c1>=0; r1++,c1--)
				if (isArrive[r][c] == isArrive[r1][c1])
					count++;
				else
					break;
			return count;
		}
		

}
