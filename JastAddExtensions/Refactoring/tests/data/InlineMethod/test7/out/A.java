class A {
  void m() {
    int i = 23;
    {
      if(i++ == 23) 
        System.out.println("magic number!");
      else 
        System.out.println("something else");
    }
  }
  void n(int i) {
    if(i == 23) 
      System.out.println("magic number!");
    else 
      System.out.println("something else");
  }
  A() {
    super();
  }
}