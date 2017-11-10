#! /bin/bash

set -eu -o pipefail

usage() {
  cat <<EOF
Usage: create_project.sh <project_name>

Arguments
    <project_name>  Name of project to create

EOF
  exit 1
}

if [[ $# < 1 ]] || [[ $1 == "-h" ]] || [[ $1 == "--help" ]]; then usage; fi

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_NAME=$1

cp -r ${SCRIPT_DIR}/skeleton ${PROJECT_NAME}
cd ${PROJECT_NAME}

for filename in $(grep -rl foobar .); do
  docker run -it --rm -v $(pwd):/app -w /app fdm1/spark_scala sed -i "s/foobar/${PROJECT_NAME}/g" ${filename}
done

docker run -it --rm -v $(pwd):/app -w /app fdm1/spark_scala sbt assembly

