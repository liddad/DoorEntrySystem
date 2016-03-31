/*
*         Copyright (c), NXP Semiconductors Gratkorn / Austria
*
*                     (C)NXP Semiconductors
*       All rights are reserved. Reproduction in whole or in part is 
*      prohibited without the written consent of the copyright owner.
*  NXP reserves the right to make changes without notice at any time.
* NXP makes no warranty, expressed, implied or statutory, including but
* not limited to any implied warranty of merchantability or fitness for any
*particular purpose, or that the use will not infringe any third party patent,
* copyright or trademark. NXP must not be liable for any loss or damage
*                          arising from its use.
*/

/** \file
* BAL Stub Component of Reader Library Framework.
* $Author: santosh.d.araballi $
* $Revision: 1.2 $
* $Date: Fri Apr 15 09:04:54 2011 $
*
* History:
*  CHu: Generated 19. May 2009
*
*/

// WiringPi
#include <wiringPi.h>
#include <wiringPiSPI.h>

//delete
#include <linux/spi/spidev.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/ioctl.h>


#include <ph_Status.h>
#include <phbalReg.h>
#include <ph_RefDefs.h>

#ifdef NXPBUILD__PHBAL_REG_STUB

#include "phbalReg_Stub.h"



phStatus_t phbalReg_Stub_Init(
                              phbalReg_Stub_DataParams_t * pDataParams,
                              uint16_t wSizeOfDataParams
                              )
{
    int boardRev;
    int ifsel_fd;
    uint8_t data[5];
    
    if (sizeof(phbalReg_Stub_DataParams_t) != wSizeOfDataParams)
    {
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);
    }
	PH_ASSERT_NULL (pDataParams);

    pDataParams->wId = PH_COMP_BAL | PHBAL_REG_STUB_ID;
    
    boardRev = piBoardRev();
    
    ifsel_fd = open("/sys/class/gpio/export",O_WRONLY);
    if (ifsel_fd == 0)
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);

    if (boardRev == 1)
        {
        sprintf(data,"0");	/* */
        }
    else
        {
        sprintf(data,"2");	/* */
        }
    write(ifsel_fd,data,1);
    close(ifsel_fd);

    if (boardRev == 1)
        {
        ifsel_fd = open("/sys/class/gpio/gpio0/direction",O_WRONLY);
        }
    else
        {
        ifsel_fd = open("/sys/class/gpio/gpio2/direction",O_WRONLY);
        }

    if (ifsel_fd == 0)
        {
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);
        }

    sprintf(data,"in");
    write(ifsel_fd,data,2);
    close(ifsel_fd);

    #ifdef NXPBUILD__PHHAL_HW_PN512
    ifsel_fd = open("/sys/class/gpio/export",O_WRONLY);
    if (ifsel_fd == 0)
        {
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);
        }

    /*** SET the RESET pin ***/

    sprintf(data,"7");  /* 512_NRSTPD */
    write(ifsel_fd,data,1);
    close(ifsel_fd);


    /* 512_NRSTPD - output */
    ifsel_fd = open("/sys/class/gpio/gpio7/direction",O_WRONLY);
    if (ifsel_fd == 0)
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);
    sprintf(data,"out");
    write(ifsel_fd,data,3);
    close(ifsel_fd);

    /*** generate the RESET sequence "1"-(wait1.2-"0"-"1" ***/

    /* 512_NRSTPD - 1 */
    ifsel_fd = open("/sys/class/gpio/gpio7/value",O_WRONLY);

    if (ifsel_fd == 0)
        {
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);
        }
    sprintf(data,"1");
    write(ifsel_fd,data,1);

    usleep(1500);

    /* 512_NRSTPD - 0 */
    //   ifsel_fd = open("/sys/class/gpio/gpio7/value",O_WRONLY);
    if (ifsel_fd == 0)
        {
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);
        }
    sprintf(data,"0");
    write(ifsel_fd,data,1);

    usleep(1500);

    /* 512_NRSTPD - 1 */
    //   ifsel_fd = open("/sys/class/gpio/gpio7/value",O_WRONLY);
    if (ifsel_fd == 0)
        {
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);
        }
    sprintf(data,"1");
    write(ifsel_fd,data,1);
    close(ifsel_fd);

    #endif /* NXPBUILD__PHHAL_HW_PN512 */

    // SPI config
    pDataParams->spiChannel     = 0;  // 0 = /dev/spidev0.0
                                      // 1 = /dev/spidev0.1
    pDataParams->spiMode	= 0;
    pDataParams->spiSpeed	= 2000000;
    
    return PH_ADD_COMPCODE(PH_ERR_SUCCESS, PH_COMP_BAL);
}

phStatus_t phbalReg_Stub_GetPortList(
    phbalReg_Stub_DataParams_t * pDataParams,
    uint16_t wPortBufSize,
    uint8_t * pPortNames,
    uint16_t * pNumOfPorts
    )
{
    /* TODO: Replace and insert code for getting the port list here */
    pDataParams = NULL;
    wPortBufSize = 0;
    pPortNames = NULL;
    *pNumOfPorts = 0;

    return PH_ADD_COMPCODE(PH_ERR_SUCCESS, PH_COMP_BAL);
}

phStatus_t phbalReg_Stub_SetPort(
                            phbalReg_Stub_DataParams_t * pDataParams,
                            uint8_t * pPortName
                            )
{
    /* TODO: Replace and insert code for setting a port name here */
    //pDataParams = NULL;
    //pPortName = NULL;

    return PH_ADD_COMPCODE(PH_ERR_SUCCESS, PH_COMP_BAL);
}

phStatus_t phbalReg_Stub_OpenPort(
                                  phbalReg_Stub_DataParams_t * pDataParams
                                  )
{
    pDataParams->spiFD = wiringPiSPISetupMode(pDataParams->spiChannel, 
                                              pDataParams->spiSpeed, 
                                              pDataParams->spiMode);
    if(pDataParams->spiFD < 0)
    {
        printf("phbalReg_R_Pi_spi_OpenPort: if (pDataParams->spiFD == 0)\n");
        return PH_ADD_COMPCODE(PH_ERR_INVALID_DATA_PARAMS, PH_COMP_BAL);
    }
    return 0;//PH_ADD_COMPCODE(PH_ERR_SUCCESS, PH_COMP_BAL);
}

phStatus_t phbalReg_Stub_ClosePort(
                                   phbalReg_Stub_DataParams_t * pDataParams
                                   )
{
    close(pDataParams->spiFD);

    return PH_ADD_COMPCODE(PH_ERR_SUCCESS, PH_COMP_BAL);
}

phStatus_t phbalReg_Stub_Exchange(
                                  phbalReg_Stub_DataParams_t * pDataParams,
                                  uint16_t wOption,
                                  uint8_t * pTxBuffer,
                                  uint16_t wTxLength,
                                  uint16_t wRxBufSize,
                                  uint8_t * pRxBuffer,
                                  uint16_t * pRxLength
                                  )
{
    *pRxLength = wiringPiSPIDataRW(pDataParams->spiChannel, 
                                   pTxBuffer, 
                                   wTxLength);
    if (*pRxLength <= 0 )
    {
        printf("read write error\n");
        return PH_ADD_COMPCODE(PH_ERR_READ_WRITE_ERROR, PH_COMP_BAL);
    }

    return 0;//PH_ADD_COMPCODE(PH_ERR_SUCCESS, PH_COMP_BAL);
}

phStatus_t phbalReg_Stub_SetConfig(
                                   phbalReg_Stub_DataParams_t * pDataParams,
                                   uint16_t wConfig,
                                   uint16_t wValue
                                   )
{
    /* TODO: Replace and insert code to configure the connection here */
    pDataParams = NULL;
    wConfig = 0;
    wValue = 0;

    return PH_ADD_COMPCODE(PH_ERR_SUCCESS, PH_COMP_BAL);
}



phStatus_t phbalReg_Stub_GetConfig(
                                   phbalReg_Stub_DataParams_t * pDataParams,
                                   uint16_t wConfig,
                                   uint16_t * pValue
                                   )
{
    /* TODO: Replace and insert code to read connection configuration here */
    pDataParams = NULL;
    wConfig = 0;
    pValue = NULL;

    return PH_ADD_COMPCODE(PH_ERR_SUCCESS, PH_COMP_BAL);
}

#endif /* NXPBUILD__PHBAL_REG_STUB */
