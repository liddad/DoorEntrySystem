cmake_minimum_required (VERSION 2.8)
project (explorenfc-cardemulation)

include_directories (
    "${PROJECT_SOURCE_DIR}/NxpRdLib_PublicRelease/intfs"
    "${PROJECT_SOURCE_DIR}/NxpRdLib_PublicRelease/types"
    "${PROJECT_SOURCE_DIR}/NxpRdLib_PublicRelease/comps/phceT2TCmdHdl/src/Sw"
    "${PROJECT_SOURCE_DIR}/NxpRdLib_PublicRelease/comps/phbalReg/src/Stub"
    "${PROJECT_SOURCE_DIR}/utils"
    "${PROJECT_SOURCE_DIR}"
)

set(EXT_LIBS  pthread  m rt wiringPi)

add_definitions(-g0 -O2)

list(APPEND SOURCE "${PROJECT_SOURCE_DIR}/main.c")
source_group(src "${PROJECT_SOURCE_DIR}/main.c")

file(GLOB_RECURSE SOURCE_TMP "${PROJECT_SOURCE_DIR}/NxpRdLib_PublicRelease/*.c" )
list(APPEND SOURCE ${SOURCE_TMP})
file(GLOB_RECURSE SOURCE_TMP "${PROJECT_SOURCE_DIR}/utils/*.c" )
list(APPEND SOURCE ${SOURCE_TMP})

add_executable(${PROJECT_NAME} ${SOURCE})
target_link_libraries(${PROJECT_NAME} ${EXT_LIBS})
	
