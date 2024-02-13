def addVariables(output_file, filename, variables):
  for variable in variables:
    variable_name = variable[0]
    variable_value = variable[2]
    variable_size = variable[3]
    variable_position = variable[4]
    output_file.write("    m_nte_" + variable_name + " = " +
    "m_sbt_" + filename + ".add(\"" + variable_name + "\", " + variable_value + ")\n")

    if variable_size[0] != -1 and variable_size[1] != -1:
      output_file.write(".withSize(" + str(variable_size[0]) + ", " + str(variable_size[1]) + ")\n")
    if variable_position[0] != -1 and variable_position[1] != -1:
      output_file.write(".withPosition(" + str(variable_position[0]) + ", " + str(variable_position[1]) + ")\n")


    output_file.write(".getEntry();\n")
  output_file.write("  }\n")
  return 0

def addVariablesDeclarations(output_file, variables, fileName):
  output_file.write("\n  ShuffleboardTab m_sbt_" + fileName + ";\n\n")
  for variable in variables:
    output_file.write("  " + "GenericEntry" + " m_nte_" + variable[0] + ";\n")
  return 0

def getVariableData(input_file):
  variableData = []  # Initialize an empty list to store variable data
  lastVariableName = ""
  variable_type = ""
  variable_value = ""
  variable_name = ""
  variable_size = [-1, -1] # Initialize an empty list to store variable size
  variable_position = [-1, -1] # Initialize an empty list to store variable position
  for line in input_file:
    line = line.strip()  # Remove leading/trailing whitespaces including '\n'

    # Skip comments, non-variable lines, and imports
    if line.startswith("--") or "=" not in line or "imports" in line:
      continue
    # remove comments and anything after them

    commentPos = line.find("--")
    if commentPos != -1:
      line = line[:commentPos]

    parts = line.split("=")

    variable_name = parts[0].strip()  # Extract variable name. note: this runs every single entry

    # If it's a nested variable, take only the parent part
    if "." in variable_name:
      variable_name = variable_name.split(".")[0]

    # Check if the variable name has changed, if so log the previous variable
    if variable_name != lastVariableName:
      if lastVariableName:  # Check if it's not the first variable
        variableData.append((lastVariableName, variable_type, variable_value, variable_size, variable_position))  # Add to the list
      # Reset variables for the new variable
      lastVariableName = variable_name
      variable_type = ""
      variable_value = ""
      variable_size = [-1] * len(variable_size)
      variable_position = [-1] * len(variable_position)

    if not variable_value:  # If it's the first entry for the variable, expect a value
      variable_value = parts[1].strip()
      
      if not variable_type:  # If type hasn't been manually set with .type=
        if "true" in variable_value or "false" in variable_value:
          variable_type = "boolean"
        elif "\"" in variable_value:
          variable_type = "String"
        elif variable_value.replace(".", "").isnumeric():  # Decimals are not numeric
          variable_type = "double"
        else:
          # most variables are doubles, so default to that
          variable_type = "double"  # Set to some default value for unknown type 

    if ".type" in line: # .type is stripped from the variable name in variable_type
      variable_type = parts[1].strip()

    if ".size" in line or ".pos" in line: # .type is stripped from the variable name in variable_type
      arrayValue = parts[1].strip().replace(" ", "") # remove whitespace
      arrayValue = arrayValue.replace("{", "").replace("}", "") # remove braces
      arrayValue = arrayValue.split(",")
      if ".size" in line:
        variable_size[0] = arrayValue[0]
        variable_size[1] = arrayValue[1]
      else:
        variable_position[0] = arrayValue[0]
        variable_position[1] = arrayValue[1]

  # Log the last variable after the loop
  if lastVariableName:
    variableData.append((lastVariableName, variable_type, variable_value, variable_size, variable_position))

  # reset file pointer to the beginning of the file
  input_file.seek(0)

  return variableData