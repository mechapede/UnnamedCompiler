.source foo3.ul
.class public foo3
.super java/lang/Object

.method public static __main()V
   .limit locals 10
   .var 0 is x I from L_0 to L_1
   .var 1 is y I from L_0 to L_1
   .var 2 is T2 I from L_0 to L_1
   .var 3 is T3 I from L_0 to L_1
   .var 4 is T4 Z from L_0 to L_1
   .var 5 is T5 Z from L_0 to L_1
   .var 6 is T6 I from L_0 to L_1
   .var 7 is T7 I from L_0 to L_1
   .var 8 is T8 I from L_0 to L_1
   .var 9 is T9 I from L_0 to L_1
   .limit stack 16
L_0:
;  T2 := 10;
   ldc 10
   istore 2
;  T0 := T2;
   iload 2
   istore 0
;  T3 := 1;
   ldc 1
   istore 3
;  T1 := T3;
   iload 3
   istore 1
;L0:;
L0:
;  T4 := T1 I< T0;
   iload 1
   iload 0
   isub
   iflt L_2
   ldc 0
   goto L_3
L_2:
   ldc 1
L_3:
   istore 4
;  T5 := Z! T4;
   iload 4
   ldc 1
   ixor 
   istore 5
;  IF T5 GOTO L1;
   iload 5
   ifne L1
;  T6 := 1;
   ldc 1
   istore 6
;  T7 := T1 I+ T6;
   iload 1
   iload 6
   iadd
   istore 7
;  T1 := T7;
   iload 7
   istore 1
;  PRINTLNI T1;
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload 1
   invokevirtual java/io/PrintStream/println(I)V
;  GOTO L0;
   goto L0
;L1:;
L1:
;  T8 := 10;
   ldc 10
   istore 8
;  T9 := T0 I+ T8;
   iload 0
   iload 8
   iadd
   istore 9
;  T0 := T9;
   iload 9
   istore 0
;  RETURN;
   return
L_1:
.end method

; BOILERPLATE CODE

.method public static main([Ljava/lang/String;)V
   .limit locals 1
   .limit stack 4
   invokestatic foo3/__main()V
   return
.end method

.method public <init>()V
   aload 0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
