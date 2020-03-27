.source fact.ul
.class public fact
.super java/lang/Object

.method public static __factorial(I)I
   .limit locals 10
   .var 0 is n I from L_0 to L_1
   .var 1 is returnValue I from L_0 to L_1
   .var 2 is T2 I from L_0 to L_1
   .var 3 is T3 Z from L_0 to L_1
   .var 4 is T4 Z from L_0 to L_1
   .var 5 is T5 I from L_0 to L_1
   .var 6 is T6 I from L_0 to L_1
   .var 7 is T7 I from L_0 to L_1
   .var 8 is T8 I from L_0 to L_1
   .var 9 is T9 I from L_0 to L_1
   .limit stack 16
L_0:
;  T2 := 1;
   ldc 1
   istore 2
;  T3 := T0 I== T2;
   iload 0
   iload 2
   isub
   ifeq L_2
   ldc 0
   goto L_3
L_2:
   ldc 1
L_3:
   istore 3
;  T4 := Z! T3;
   iload 3
   ldc 1
   ixor 
   istore 4
;  IF T4 GOTO L0;
   iload 4
   ifne L0
;  T5 := 1;
   ldc 1
   istore 5
;  T1 := T5;
   iload 5
   istore 1
;  GOTO L1;
   goto L1
;L0:;
L0:
;  T6 := 1;
   ldc 1
   istore 6
;  T7 := T0 I- T6;
   iload 0
   iload 6
   isub
   istore 7
;  T8 := CALL factorial(T7);
   iload 7
   invokestatic fact/__factorial(I)I
   istore 8
;  T9 := T0 I* T8;
   iload 0
   iload 8
   imul
   istore 9
;  T1 := T9;
   iload 9
   istore 1
;L1:;
L1:
;  RETURN T1;
   iload 1
   ireturn
L_1:
.end method

.method public static __main()V
   .limit locals 3
   .var 0 is T0 Ljava/lang/String; from L_0 to L_1
   .var 1 is T1 I from L_0 to L_1
   .var 2 is T2 I from L_0 to L_1
   .limit stack 16
L_0:
;  T0 := "The factorial of 8 is ";
   ldc "The factorial of 8 is "
   astore 0
;  PRINTU T0;
   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload 0
   invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V
;  T1 := 8;
   ldc 8
   istore 1
;  T2 := CALL factorial(T1);
   iload 1
   invokestatic fact/__factorial(I)I
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
   invokestatic fact/__main()V
   return
.end method

.method public <init>()V
   aload 0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
