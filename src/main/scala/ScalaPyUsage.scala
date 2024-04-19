import me.shadaj.scalapy.py

object ScalaPyUsage {
  @main def main(): Unit = {
    py.exec("print(0)")
    py.exec("import json")
  }
}
