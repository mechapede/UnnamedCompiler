.source ar2.ul
.class public ar2
.super java/lang/Object

.method public static __main()V
   .limit locals 15
   .var 0 is x [I from L_0 to L_1
   .var 1 is i I from L_0 to L_1
   .var 2 is T2 I from L_0 to L_1
   .var 3 is T3 I from L_0 to L_1
   .var 4 is T4 I from L_0 to L_1
   .var 5 is T5 I from L_0 to L_1
   .var 6 is T6 I from L_0 to L_1
   .var 7 is T7 I from L_0 to L_1
   .var 8 is T8 I from L_0 to L_1
   .var 9 is T9 I from L_0 to L_1
   .var 10 is T10 Z from L_0 to L_1
   .var 11 is T11 Z from L_0 to L_1
   .var 12 is T12 I from L_0 to L_1
   .var 13 is T13 I from L_0 to L_1
   .var 14 is T14 I from L_0 to L_1
   .limit stack 16
L_0:
;  T0 := NEWARRAY I 3;
   ldc 3
   newarray int
   astore 0
;  T2 := 1;
   ldc 1
   istore 2
;  T3 := 0;
   ldc 0
   istore 3
;  T0[T3] := T2;
   aload 0
   iload 3
   iload 2
   iastore
;  T4 := 2;
   ldc 2
   istore 4
;  T5 := 1;
   ldc 1
   istore 5
;  T0[T5] := T4;
   aload 0
   iload 5
   iload 4
   iastore
;  T6 := 3;
   ldc 3
   istore 6
;  T7 := 2;
   ldc 2
   istore 7
;  T0[T7] := T6;
   aload 0
   iload 7
   iload 6
   iastore
;  T8 := 0;
   ldc 0
   istore 8
;  T1 := T8;
   iload 8
   istore 1
;L0:;
L0:
;  T9 := 3;
   ldc 3
   istore 9
;  T10 := T1 I< T9;
   iload 1
   iload 9
   isub
   iflt L_2
   ldc 0
   goto L_3
L_2:
   ldc 1
L_3:
   istore 10
;  T11 := Z! T10;
   iload 10
   ldc 1
   ixor 
   istore 11
;  IF T11 GOTO L1;
   iload 11
   ifne L1
;  T12 := T0[T1];
   aload 0
   iload 1
   iaload
   istore 12
;  PRINTLNI T12;
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload 12
   invokevirtual java/io/PrintStream/println(I)V
;  T13 := 1;
   ldc 1
   istore 13
;  T14 := T1 I+ T13;
   iload 1
   iload 13
   iadd
   istore 14
;  T1 := T14;
   iload 14
   istore 1
;  GOTO L0;
   goto L0
;L1:;
L1:
;  RETURN;
   return
L_1:
.end method

; BOILERPLATE CODE

.method public static main([Ljava/lang/String;)V
   .limit locals 1
   .limit stack 4
   invokestatic ar2/__main()V
   return
.end method

.method public <init>()V
   aload 0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
