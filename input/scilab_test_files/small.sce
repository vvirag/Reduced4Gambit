A=[0 0 0 0;
0 0 0 0;
0 7 3 5;
0 0 9 0;
0 0 5 0;
0 0 0 1;
0 0 0 9]

B=[0 0 0 0;
0 8 0 0;
0 7 2 4;
0 0 7 0;
0 0 3 0;
0 0 0 1;
0 0 0 4]

e_=[1; 0; 1; 0]

f_=[1; 1]

E_b = [1 0 0 0;
0 1 0 0;
0 0 1 0;
0 0 0 1]

E_i =[ 0  0  0;
0 -1 -1;
0  1  1;
1 -1 -1]

F_b =[1 0;
0 1]

F_i =[0 0;
1 1]





p=[]
P=[]
q=[]
Q=[]

a_=[]
b_=[]
A_=[]
B_=[]

function printMatrices()
   disp('a_')
   disp(a_)
   disp('b_')
   disp(b_)
   disp('A_')
   disp(A_)
   disp('B_')
   disp(B_)   
endfunction

function printHelpMatrices()
   disp('p')
   disp(p)
   disp('P')
   disp(P)
   disp('q')
   disp(q)
   disp('Q')
   disp(Q)   
endfunction


function calculateMatrices()

    p = inv(E_b)*e_
    P = -1 * inv(E_b)*E_i
    
    q = inv(F_b)*f_
    Q = -1 * inv(F_b)*F_i
    
    as1 = size(A, "r") - size(P, "r")
    as2 = size(A, "c") - size(q, "r")
    a_ = [P ; eye(as1, size(P, "c"))]' * A * [q ; zeros(as2, size(q, "c"))]
    
    bs1 = size(B, "r") - size(p, "r")
    bs2 = size(B, "c") - size(Q, "r")
    b_ = [p ; zeros(bs1, size(p, "c"))]' * B * [Q ; eye(bs2, size(Q, "c"))]
        
    As1 = size(A, "r") - size(P, "r")
    As2 = size(A, "c") - size(Q, "r")
    A_ = [P ; eye(As1, size(P, "c"))]' * A * [Q ; eye(As2, size(Q, "c"))]
    
    Bs1 = size(B, "r") - size(P, "r")
    Bs2 = size(B, "c") - size(Q, "r")
    B_ = [P ; eye(Bs1, size(P, "c"))]' * B * [Q ; eye(Bs2, size(Q, "c"))]
    
    printMatrices
    
endfunction

calculateMatrices

