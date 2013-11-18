//package typeChecker
//
//import ast._
//
//
//class BottomUpTypeChecker {
//  object ScopedType
//  {
//    implicit def typeToScoped(typ: Type) = new ScopedType(typ)
//  }
//
//  type Env = Map[String,Type]
//  case class ScopedType(typ: Type, env: Env = Map.empty)
//
//  var freshVariableCounter: Int = 0
//  def checkIsAssignableTo(target: ScopedType, value: ScopedType): Env = {
//    evaluateType(value) match {
//      case TypeVariable(name) => {
//        target match {
//          case TypeVariable(targetName) if name == targetName =>
//          case _ => variableTypes.put(name, target)
//        }
//      }
//      case newValue => {
//        if (target.isInstanceOf[TypeVariable])
//        {
//          checkIsAssignableTo(newValue,target)
//          return
//        }
//        val newTarget = evaluateType(target)
//        (newTarget, newValue) match {
//          case (LambdaType(firstName, firstInput, firstOutput), LambdaType(secondName, secondInput, secondOutput)) => {
//            variableTypes.push()
//            val variableInstance = getFreshVariable
//            if (variableTypes.get(firstName).isEmpty)
//            {
//              variableTypes.put(firstName,variableInstance)
//            }
//            if (variableTypes.get(secondName).isEmpty)
//            {
//              variableTypes.put(secondName,variableInstance)
//            }
//            checkIsAssignableTo(firstInput, secondInput)
//            checkIsAssignableTo(secondOutput, firstOutput)
//            variableTypes.pop()
//          }
//          case (IntType, IntType) => {}
//          case _ => throw new RuntimeException(s"type $target does not match type $value")
//        }
//      }
//    }
//  }
//
//  def checkEquals(first: ScopedType, second: ScopedType): Env = {
//    checkIsAssignableTo(first, second)
//    // checkIsAssignableTo(second, first)
//  }
//
//  def getType(expression: Expression, env: Env): ScopedType = expression match {
//    case IntValue(_) => IntType
//    case Call(callee, argument) => {
//      val calleeType = getType(callee)
//      val argumentType = getType(argument)
//      val outputType = getFreshVariable
//      checkIsAssignableTo(new LambdaType(outputType.name, argumentType, outputType), calleeType)
//      val result = evaluateType(outputType)
//      variableTypes.pop()
//      result
//    }
//    case Variable(name) => {
//      new TypeVariable(name)
//    }
//    case If(condition, elseExpression, thenExpression) => {
//      val env1 = checkIsAssignableTo(IntType, getType(condition, env))
//      val elseType = getType(elseExpression, env1)
//      val thenType = getType(thenExpression, elseType.env)
//      val env2 = checkEquals(elseType, thenType)
//      new ScopedType(elseType.typ,env2)
//    }
//    case Lambda(name, body) => {
//      val scopedBodyType = getType(body, env)
//      new ScopedType(new LambdaType(name, new TypeVariable(name), scopedBodyType.typ), scopedBodyType.env)
//    }
//
//    case Let(name, value, body) => {
//      val valueType = getType(value, env)
//      val env2 = checkIsAssignableTo(valueType, new TypeVariable(name))
//      getType(body, env2)
//    }
//    case Addition(first, second) => {
//      val firstType = getType(first, env)
//      val env1 = checkIsAssignableTo(IntType, firstType)
//      val env2 = checkIsAssignableTo(IntType, getType(second, env1))
//      new ScopedType(IntType,env2)
//    }
//    case Equals(first, second) => {
//      val firstType = getType(first, env)
//      val env1 = checkIsAssignableTo(IntType, firstType)
//      val env2 = checkIsAssignableTo(IntType, getType(second, env1))
//      new ScopedType(IntType,env2)
//    }
//  }
//
//  def getFreshVariable: TypeVariable = {
//    val freshVariable = freshVariableCounter
//    freshVariableCounter += 1
//    new TypeVariable(freshVariable.toString)
//  }
//}
