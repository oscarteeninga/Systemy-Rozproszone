
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
  sequence<long> seqOfNumbers;
  enum operation { MIN, MAX, AVG };
  

  interface Calc
  {
    long add(int a, int b);
    long subtract(int a, int b);
    double avg(seqOfNumbers numbers);
  };

};

#endif
