package com.tution.wanandroidkotlin;

public class Test {

//    public double Power(double base, int n) {
//        double res=1 ,cur = base;
//        int e;
//        if (n>0){}
//    }

     public static class TreeNode {
     int val = 0;
     TreeNode left = null;
     TreeNode right = null;

     public TreeNode(int val) {
     this.val = val;

     }

         @Override
         public String toString() {
             return "TreeNode{" +
                     "val=" + val +
                     ", left=" + left +
                     ", right=" + right +
                     '}';
         }
     }
    public static class Solution {
        public void Mirror(TreeNode root) {
            if(root==null)
                return;
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            Mirror(root.left);
            Mirror(root.right);
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        Solution solution = new Solution();
        solution.Mirror(treeNode);
        System.out.println(treeNode);
    }
}
