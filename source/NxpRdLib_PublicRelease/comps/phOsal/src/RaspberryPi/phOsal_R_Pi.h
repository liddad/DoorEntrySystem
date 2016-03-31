/*
 *         Copyright (c), NXP Semiconductors Bangalore / India
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
 * LPC12xx OSAL Component of Reader Library Framework.
 * $Author: nxp $
 * $Revision: 1.0 $
 * $Date: Wed Apr 03 2013 $
 *
 * History:
 *
 */

#ifndef PHOSAL_RPI_H
#define PHOSAL_RPI_H


phStatus_t phOsal_RPi_Init(phOsal_RPi_DataParams_t * pDataParams);

phStatus_t phOsal_RPi_Mem_Malloc(
        phOsal_RPi_DataParams_t * pDataParams,  /**< [In] Pointer to this layers parameter structure. */
        uint16_t wNum,                          /**< [In] Number of elements to allocate */
        uint8_t bSize,                          /**< [In] Size of each element */
        uint8_t bEvent,                         /**< [In] Event for transient variable */
        void ** pMem                            /**< [Out] Pointer to Memory allocated */
);

phStatus_t phOsal_RPi_Mem_Copy(
        phOsal_RPi_DataParams_t * pDataParams,  /**< [In] Pointer to this layers parameter structure. */
//        void * pSrc,                          /**< [In] Pointer to source */
        uint8_t  * pSrc,                        /**< [In] Pointer to source */
        uint16_t wSrcOff,                       /**< [In] Source pointer offset */
//        void * pDst,                          /**< [In] Pointer to destination */
        uint8_t  * pDst,                        /**< [In] Pointer to destination */
        uint16_t wDstOff,                       /**< [In] Destination pointer offset*/
        uint16_t wSize                          /**< [In] Size of the memory to copy */
);

phStatus_t phOsal_RPi_Timer_Wait(
        phOsal_RPi_DataParams_t * pDataParams,  /**<[In] Data structure representing this component */
        uint8_t bTimerDelayUnit,                /**<[In] The time unit in which delay is represented */
        uint16_t wDelay                         /**<{in] Amount of delay by which execution is to be delayed */
);

phStatus_t phOsal_RPi_GetMemory(
        phOsal_RPi_DataParams_t *pDataParams,   /**< [In] Pointer to this layers parameter structure. */
        uint32_t dwLength,                      /**< [In] Required memory length */
        void ** pMem                            /**< [Out] Pointer to Memory allocated */
);

phStatus_t phOsal_RPi_FreeMemory(
        phOsal_RPi_DataParams_t *pDataParams,   /**< [In] Pointer to this layers parameter structure. */
        void * ptr                              /**< [In] Pointer to memory to be freed */
);

#endif /* PHOSAL_R_PI_H */

/*==============================================================================================
 *   End of File
 ---------------------------------------------------------------------------------------------*/

