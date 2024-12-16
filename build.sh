#!/bin/bash

# Get the directory where this script is located (root of your project)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Define paths
SEARCH_DIR="$SCRIPT_DIR" 
SERVICE_DIRS=("TODO-Liste-Session" "List-Service" "Server" "User-Service")  # Directories to search within the project
FILE_TYPE="*.jar"  # File type to search for in the target directory
SUCCESS_LIST=() # List to store result of build

# Define actions for each condition
not_found_action() {
    echo -e "Directory $child_dir does not have a target with a $FILE_TYPE file or there is no $FILE_TYPE file. Building service...\n\n"
    cd "$child_dir" && mvn package  # Run mvn package in the child directory (parent of target)
    if [[ $? -eq 0 ]]; then
        SUCCESS_LIST+=("${child_dir##*/} build has been successful.")
    else
        SUCCESS_LIST+=("${child_dir##*/} build has not been successful.")
    fi
    printf "\n%.0s" {1..10}
}

file_found_action() {
    echo -e "Directory $child_dir has a target with a $FILE_TYPE file. No build needed.\n"
}

# Loop through each service directory to check for target and .jar files
for dir in "${SERVICE_DIRS[@]}"; do
    child_dir="$SEARCH_DIR/$dir"  # Define the child directory path
    target_dir="$child_dir/target"  # Define the target directory path

    # Check if target directory exists
    if [[ -d "$target_dir" ]]; then
        # Target directory exists, check for .jar files
        if ls "$target_dir"/$FILE_TYPE 1> /dev/null 2>&1; then
            # .jar file found, run file_found_action
            file_found_action
        else
            # No .jar file in target, run not_found_action in parent directory (child_dir)
            not_found_action
        fi
    else
        # Target directory does not exist, run not_found_action in parent directory (child_dir)
        not_found_action
    fi
done
if (( ${#SUCCESS_LIST[@]} )); then
    for service in "${SUCCESS_LIST[@]}"; do
        echo "$service"
    done
fi
cd "$SCRIPT_DIR" && docker compose --parallel 1 up --build --detach  # Run Docker Compose in script directory