.source expr.ul
.class public expr
.super java/lang/Object

.method public static __main()V
   .limit locals 11
   .var 0 is x I from L_0 to L_1
   .var 1 is y I from L_0 to L_1
   .var 2 is z I from L_0 to L_1
   .var 3 is T3 I from L_0 to L_1
   .var 4 is T4 I from L_0 to L_1
   .var 5 is T5 I from L_0 to L_1
   .var 6 is T6 I from L_0 to L_1
   .var 7 is T7 I from L_0 to L_1
   .var 8 is T8 I from L_0 to L_1
   .var 9 is T9 I from L_0 to L_1
   .var 10 is T10 I from L_0 to L_1
   .limit stack 16
L_0:
;  T3 := 1;
   ldc 1
   istore 3
;  T0 := T3;
   iload 3
   istore 0
;  T4 := 2;
   ldc 2
   istore 4
;  T1 := T4;
   iload 4
   istore 1
;  T5 := 3;
   ldc 3
   istore 5
;  T2 := T5;
   iload 5
   istore 2
;  T6 := 1;
   ldc 1
   istore 6
;  T7 := T1 I+ T6;
   iload 1
   iload 6
   iadd
   istore 7
;  T8 := T0 I* T7;
   iload 0
   iload 7
   imul
   istore 8
;  T9 := 7;
   ldc 7
   istore 9
;  T10 := T8 I+ T9;
   iload 8
   iload 9
   iadd
   istore 10
;  T2 := T10;
   iload 10
   istore 2
;  PRINTLNI T2;
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload 2
   invokevirtual java/io/PrintStream/println(I)V
;  RETURN;
   return
L_1:
.end method

; BOILERPLATE CODE

.method public static main([Ljava/lang/String;)V
   .limit locals 1
   .limit stack 4
   invokestatic expr/__main()V
   return
.end method

.method public <init>()V
   aload 0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
