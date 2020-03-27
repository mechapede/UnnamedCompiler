.source array_max.ul
.class public array_max
.super java/lang/Object

.method public static __arrayMax([I)I
   .limit locals 15
   .var 0 is vals [I from L_0 to L_1
   .var 1 is i I from L_0 to L_1
   .var 2 is max I from L_0 to L_1
   .var 3 is T3 I from L_0 to L_1
   .var 4 is T4 I from L_0 to L_1
   .var 5 is T5 I from L_0 to L_1
   .var 6 is T6 I from L_0 to L_1
   .var 7 is T7 Z from L_0 to L_1
   .var 8 is T8 Z from L_0 to L_1
   .var 9 is T9 I from L_0 to L_1
   .var 10 is T10 Z from L_0 to L_1
   .var 11 is T11 Z from L_0 to L_1
   .var 12 is T12 I from L_0 to L_1
   .var 13 is T13 I from L_0 to L_1
   .var 14 is T14 I from L_0 to L_1
   .limit stack 16
L_0:
;  T3 := 1;
   ldc 1
   istore 3
;  T1 := T3;
   iload 3
   istore 1
;  T4 := 0;
   ldc 0
   istore 4
;  T5 := T0[T4];
   aload 0
   iload 4
   iaload
   istore 5
;  T2 := T5;
   iload 5
   istore 2
;L0:;
L0:
;  T6 := 3;
   ldc 3
   istore 6
;  T7 := T1 I< T6;
   iload 1
   iload 6
   isub
   iflt L_2
   ldc 0
   goto L_3
L_2:
   ldc 1
L_3:
   istore 7
;  T8 := Z! T7;
   iload 7
   ldc 1
   ixor 
   istore 8
;  IF T8 GOTO L1;
   iload 8
   ifne L1
;  T9 := T0[T1];
   aload 0
   iload 1
   iaload
   istore 9
;  T10 := T2 I< T9;
   iload 2
   iload 9
   isub
   iflt L_4
   ldc 0
   goto L_5
L_4:
   ldc 1
L_5:
   istore 10
;  T11 := Z! T10;
   iload 10
   ldc 1
   ixor 
   istore 11
;  IF T11 GOTO L2;
   iload 11
   ifne L2
;  T12 := T0[T1];
   aload 0
   iload 1
   iaload
   istore 12
;  T2 := T12;
   iload 12
   istore 2
;  GOTO L3;
   goto L3
;L2:;
L2:
;L3:;
L3:
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
;  RETURN T2;
   iload 2
   ireturn
L_1:
.end method

.method public static __main()V
   .limit locals 8
   .var 0 is vals [I from L_0 to L_1
   .var 1 is T1 I from L_0 to L_1
   .var 2 is T2 I from L_0 to L_1
   .var 3 is T3 I from L_0 to L_1
   .var 4 is T4 I from L_0 to L_1
   .var 5 is T5 I from L_0 to L_1
   .var 6 is T6 I from L_0 to L_1
   .var 7 is T7 I from L_0 to L_1
   .limit stack 16
L_0:
;  T0 := NEWARRAY I 3;
   ldc 3
   newarray int
   astore 0
;  T1 := 1;
   ldc 1
   istore 1
;  T2 := 0;
   ldc 0
   istore 2
;  T0[T2] := T1;
   aload 0
   iload 2
   iload 1
   iastore
;  T3 := 7;
   ldc 7
   istore 3
;  T4 := 1;
   ldc 1
   istore 4
;  T0[T4] := T3;
   aload 0
   iload 4
   iload 3
   iastore
;  T5 := 5;
   ldc 5
   istore 5
;  T6 := 2;
   ldc 2
   istore 6
;  T0[T6] := T5;
   aload 0
   iload 6
   iload 5
   iastore
;  T7 := CALL arrayMax(T0);
   aload 0
   invokestatic array_max/__arrayMax([I)I
   istore 7
;  PRINTI T7;
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload 7
   invokevirtual java/io/PrintStream/print(I)V
;  RETURN;
   return
L_1:
.end method

; BOILERPLATE CODE

.method public static main([Ljava/lang/String;)V
   .limit locals 1
   .limit stack 4
   invokestatic array_max/__main()V
   return
.end method

.method public <init>()V
   aload 0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
