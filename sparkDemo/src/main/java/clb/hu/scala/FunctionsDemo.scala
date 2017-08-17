package clb.hu.scala
/**
 * Scala详解---------控制结构和函数
 * 
 * 注：Scala中的分号（；）绝大多数情况下不是必需的。
 * 注： 在Scala中没有Switch语句，其存在强大的模式匹配机制
 * 注：Scala中没有提供break和continue语句退出循环。
 */
object FunctionsDemo {
  def main(args: Array[String]): Unit = {
    
    println("="*60)
    test1()           //11 12 13 21 22 23 31 32 33
  
    
    println("\n"+"="*60) 
    test2()           //hieflmlmop
                      //h  i  e  f  l  m  l  m  o  p  
                      //Vector(h, e, l, l, o, i, f, m, m, p)
                      //h  e  l  l  o  i  f  m  m  p  
    
    
    println("\n"+"="*60) 
    println("求1~5的和（不使用return语句）"+sum1(5))  //15
    println("求1~5的和（使用return语句）"+sum2(5))  //15
    println("求1~5的阶乘："+fac(5))   //120
    
    
    println("\n"+"="*60) 
    outputName("詹姆斯")             //[詹姆斯]
    outputName("詹姆斯","<<")        //<<詹姆斯]
    outputName("詹姆斯","<<",">>")   //<<詹姆斯>>
    outputName("詹姆斯",right="}")   //[詹姆斯}

    
    println("\n"+"="*60)
    println("传一个参数的和："+varibleLength(1))
    println("传入两个参数的和："+varibleLength(1,2))
    println("传入五个参数的和："+varibleLength(1,2,3,4,5))
    println("传入一个序列的和："+varibleLength(1 to 5:_*))
    
    println("传入一个序列的和："+varibleLength2(1 to 5:_*))
    
  }
  
  
  /**
   * 使用变量<-表达式形式提供多个生成器，分号分割
   */
  def  test1()={
    for(i <- 1 to 3;j <- 1 to 3)
      print(10*i+j+" ")
  }
  
  /**
   * for推导式生成器的集合与它的第一个生成器的类型是兼容的。
   */
  def test2()={
    val b = for(c <- "hello";j <- 0 to 1) yield (c+j).toChar
    println(b)
    for(i <- b)
      print(i+"  ")
      println("")
      
    val c = for(i <- 0 to 1;j <- "hello") yield (i+j).toChar
    println(c)
    for(i <- c)
      print(i+"  ")
  }
  
  
  /**
   * 如果函数体需要多个表达式完成，可以使用代码块。块的最后一个表达式的值就是函数的返回值。
   * 此函数result即是函数的返回值。如果使用return返回result的值，那么需要明确指定函数返回类型
   * 
   * 如果是递归函数，同样需要指定返回类型。
   */
  def sum1(n:Int)={
    var result = 0
    for(i <- 1 to n)
      result+=i
    result
  }
  
  def sum2(n:Int):Int={
    var result = 0
    for(i <- 1 to n)
      result+=i
     return  result
  }
  
  def fac(n:Int):Int={
    if(n<=0) 1
    else n*fac(n-1)
  }
  
  
  /**
   * 默认参数和代码参数：
   * 有些情况下我们不需要给出全部参数，对应这类函数我们可以使用默认参数，当然你需要知道参数顺序或者参数名称。
   */
  def outputName(name:String,left:String="[",right:String="]"){
    println(left+name+right)
  }
  
  /**
   * 变长参数：实现一个可以接受可变长度的参数列表的函数
   * 实现一个序列作为参数传入上述函数中，需要追加 _*，告诉编译器希望把这个参数当做序列处理。
   */
  def varibleLength(args:Int*)={
    var result = 0
    for(arg <- args)
      result+=arg
      result
  }
  
  //序列的head是参数args的首个元素，而tail是所有其它的元素序列，这是个Seq，需要用 _*将它转为参数序列。
  def varibleLength2(args : Int*):Int = {  
      if(args.length == 0 )  
        0  
      else  
        args.head + varibleLength2(args.tail: _*)  
   }
  
}